package com.flipkart.config;

import com.flipkart.utils.ConfigurationManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.google.common.collect.ObjectArrays.concat;


public class TestSetUpFactory extends RetryAnalyzer {

    public static ExtentTestFactory extentTestFactory;
    public ConfigurationManager prop;
    public AppiumDriverLocalService service;
    public AppiumServiceBuilder builder;
    public AppiumDriver<WebElement> driver;
    public File WORKING_DIRECTORY;
    private static ExtentReports extent;
    public static String filePath = System.getProperty("user.dir") + "/Reports/" + extentTestFactory.getPath();

    private static final String ACCESS_TOKEN = "8108a13ee4dd4b8a885baac62f9fc31ee35dfa2329b64349af20230ead87a768";  // Change this access token
    //8108a13ee4dd4b8a885baac62f9fc31ee35dfa2329b64349af20230ead87a768
//86f9958777d140389552a17c0a9e68e4e0deebe2ab6b4be09484e57a9a809653
    public List<String> processID;
    private static final String[] OS_LINUX_RUNTIME = {"/bin/bash", "-l", "-c"};
    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    DeviceHelper deviceHelper;

    /**
     * Constructor to load config.properties
     */
    TestSetUpFactory() {
        try {
            prop = ConfigurationManager.getInstance();
            this.driver = ContextManager.getDriver();
            deviceHelper = new DeviceHelper(driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Extent Config xml file
     */
    public void loadExtentFile(String Devices, String firstPort) {
        System.out.print("***Devices2****=="+Devices);
        System.out.print("***firstPort2****=="+firstPort);
        new File(filePath);
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());

        if (prop.getProperty("Runner").equalsIgnoreCase("Local")) {
            startAppiumServer(Devices, firstPort);
//            try (ServerSocket ss = new ServerSocket(0)) {
//                long freeport = ss.getLocalPort();
//            } catch (Exception e) {
//
//            }
        }
    }

    /**
     * Start Appium Server
     */
    public void startAppiumServer(String Devices, String firstPort) {
        String ipAddress = "0.0.0.0";

        if (prop.getProperty("Parallel").equalsIgnoreCase("true") || prop.getProperty("stf").equalsIgnoreCase("true")) {
            System.out.print("Devices******=="+Devices);
            System.out.print("***Devices****=="+Devices);
            System.out.print("***firstPort****=="+firstPort);
            int devices = Integer.parseInt(Devices);
            int port = Integer.parseInt(firstPort);
            processID = new ArrayList<>();
            try {
                for (int i = 0; i < devices; i++) {

                    ProcessBuilder builder = new ProcessBuilder("sh", "-c", "echo $$; appium -a " + ipAddress + " -p " + (port + i) + " --session-override");
                    builder.start();

                    BufferedReader stdInput = new BufferedReader(new
                            InputStreamReader(builder.start().getInputStream()));

                    System.out.println("Here is the standard output of the command:\n");
                    String s;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                        processID.add(i, s);
                        break;
                    }
                    stdInput.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                service = AppiumDriverLocalService.buildDefaultService();
                writeAppiumServerLogsIntoFile();
                service.start();

            } catch (Exception e) {

            }
        }
    }

    public void writeAppiumServerLogsIntoFile() {
        File logFile = new File(System.getProperty("user.dir") + "/logs/" + "AppiumServerLogs.txt");
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withLogFile(logFile));
    }

    /**
     * Stop Appium Server
     */
    public void stopAppiumServer(String Devices) {
        if (prop.getProperty("stf").equalsIgnoreCase("true") || prop.getProperty("Parallel").equalsIgnoreCase("true")) {
            try {
                int devices = Integer.parseInt(Devices);
                for (int i = 0; i < devices; i++) {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder("kill " + processID.get(i));
                        processBuilder.start();
                    } catch (Exception e) {
                        ProcessBuilder processBuilder = new ProcessBuilder("kill " + processID.get(i));
                        processBuilder.start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            service.stop();
        }

    }

    public void configExtenTest(String className) {

        ExtentTest parent = extent.createTest(className);
        ExtentTestFactoryParent.setExtentTest(parent);
    }

    public String getDeviceModel(String deviceSerial) {
        String deviceModel;
        String command = "adb -s " + deviceSerial + " shell getprop ro.product.model";
        String[] allCommand;
        try {

            allCommand = concat(OS_LINUX_RUNTIME, command);

            ProcessBuilder pb = new ProcessBuilder(allCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));


            while ((deviceModel = in.readLine()) != null) {
                System.out.println("temp line: " + deviceModel);
                break;
            }
            System.out.println("result after command: " + deviceModel);
            in.close();
            return deviceModel;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void startTest(Method name, String deviceName) {
        ExtentTest child;
        if (prop.getProperty("stf").equalsIgnoreCase("true") || prop.getProperty("Parallel").equalsIgnoreCase("true")) {
            child = ExtentTestFactoryParent.getExtentTest()
                    .createNode(name.getName()).assignCategory(getDeviceModel(deviceName));
        } else {
            child = ExtentTestFactoryParent.getExtentTest()
                    .createNode(name.getName());
        }
        ExtentTestFactory.setExtentTest(child);
    }

    /**
     * Launch App
     */

    public void launchApplication(@Optional String PlatformVersion, @Optional String
            DeviceName, @Optional String PORT, @Optional String SystemPort) {

        try {
            System.out.println("EndPoint used is " + prop.getProperty("endpoint"));
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("newCommandTimeout", prop.getProperty("newCommandTimeout"));
            capabilities.setCapability("platformName", prop.getProperty("platformName"));
            capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
            // capabilities.setCapability(CapabilityType.PLATFORM, device.getDeviceProductName());
            capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
            capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
            capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
            capabilities.setCapability("noSign", prop.getProperty("noSign"));
            capabilities.setCapability("automationName", prop.getProperty("automationName"));
            capabilities.setCapability("systemPort", prop.getProperty("systemPort"));
            capabilities.setCapability("uiautomator2ServerLaunchTimeout", "80000");
            capabilities.setCapability("adbExecTimeout", "80000");
            capabilities.setCapability("app", System.getProperty("user.dir") + "/" + prop.getProperty("app"));
            capabilities.setCapability("enforceAppInstall", "true");
            capabilities.setCapability("skipDeviceInitialization",true);
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
            driver = new AndroidDriver<WebElement>(new URL(prop.getProperty("endpoint")), capabilities);
            //getPerformanceData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ContextManager.setDriver(driver);
    }

    @DataProvider
    public Object[][] parallelExec() {
        return new Object[][]{
                {prop.getProperty("deviceSerial1")},
                {prop.getProperty("deviceSerial2")},
        };
    }

    /**
     * Quiting the Driver
     */
    public void closeApplication() {

        //Delete driver instance
        if (prop.getProperty("Runner").equalsIgnoreCase("Local")) {
            if (ContextManager.getDriver() != null) {
                ContextManager.getDriver().quit();
            }
        }
    }

    /**
     * Flush the report and Stop Appium server
     */
    public void flushReport(@Optional String Devices) throws Exception {

        if (prop.getProperty("Runner").equalsIgnoreCase("Local")) {
            stopAppiumServer(Devices);
        }
        Thread.sleep(2000);
        extent.flush();
        //report.flush();
        System.out.println("Extent Reports is available here =>> " + filePath);
        System.out.println("Pcloudy Reports is available here =>> " + System.getProperty("user.dir") + "/Reports/Consolidated Reports.html");
        System.out.println("After Suite Executed");
    }

    /**
     * @return ExtentHtmlReporter Instance
     */
    private static ExtentHtmlReporter getHtmlReporter() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        htmlReporter.config().setDocumentTitle("Sharechat-Automation");
        htmlReporter.config().setReportName("Mobile Automation");
        htmlReporter.config().setTheme(Theme.DARK);
        return htmlReporter;
    }

    /**
     * Result Status Capture
     *
     * @param result
     */
    public void testResultCapture(ITestResult result) {

        //getPerformanceData();
        //ExtentTestFactoryParent.getExtentTest().info(" "+result.getStatus());
        System.out.println("===============================Check status======================================" + result.getStatus());
        /**
         * Success Block
         */
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestFactory.getExtentTest().log(Status.PASS, result.getMethod().getMethodName() + " Passed");
        }
/**
 * Failure Block
 */
        if (result.getStatus() == ITestResult.FAILURE) {
            if (result.getThrowable().getMessage().contains("Skipped By retry")) {
                ExtentTestFactory.getExtentTest().getExtent().removeTest(ExtentTestFactory.getExtentTest());
            } else {
                StringWriter exceptionInfo = new StringWriter();
                result.getThrowable().printStackTrace(new PrintWriter(exceptionInfo));

                String methodClassName = result.getThrowable().getMessage();
                for (StackTraceElement stack : result.getThrowable().getStackTrace()) {
                    if (stack.getClassName().contains("co.sharechat.pages.Actions")) {
                        methodClassName = methodClassName + "   Failed in Class: " + stack.getClassName() +
                                ",  in Method : " + stack.getMethodName() +
                                ",  and Line : " + stack.getLineNumber();
                        break;
                    }
                }

                ExtentTestFactory.getExtentTest().fail(methodClassName);
                ExtentTestFactory.getExtentTest().addScreenCaptureFromBase64String(takeScreenShot());

            }
        }

    }

    protected String takeScreenShot() {

        if (prop.getProperty("Runner").equalsIgnoreCase("Local")) {

            try {
                File snapshotTmpFile = ContextManager.getDriver().getScreenshotAs(OutputType.FILE);
                File snapshotFile = new File(System.getProperty("user.dir") + "/Reports/", snapshotTmpFile.getName());
                FileUtils.moveFile(snapshotTmpFile, snapshotFile);
                String encodeImage = convertImageToBase64(snapshotFile);
                snapshotFile.delete();
                return encodeImage;
            } catch (WebDriverException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * Convert Image to Base64
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String convertImageToBase64(File file) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return "data:image/jpg;base64, " + encodedString;
    }


}
