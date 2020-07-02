package com.flipkart.utils;

import com.flipkart.config.WebDriverListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

@Listeners({WebDriverListener.class})
public class TestSetUp extends WebDriverListener{

    @Parameters({"Devices", "FirstPort"})
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(@Optional String Devices, @Optional String FirstPort) throws Exception {
        System.out.print("***Devices****=="+Devices);
        System.out.print("***FirstPort****=="+FirstPort);
        loadExtentFile(Devices, FirstPort);
//        loadExtentFile("3", "4751");
    }


    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
       // getPerformanceData();
        //  makeReportsFolder();
        System.out.println("=============================BeforeTest in TestSetup is Executed==============================");
    }

    @Parameters({"PlatformVersion", "DeviceName", "Port", "SystemPort", "DeviceId"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(@Optional String PlatformVersion, @Optional String DeviceName, @Optional String Port, @Optional String SystemPort, @Optional String DeviceId) {
        System.out.println("=============================BeforeClass in TestSetup is executing==============================");
        configExtenTest(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1));
        launchApplication(PlatformVersion, DeviceName, Port, SystemPort);
  //      new CommonPageActions().setExperimentValueBasedOnDeviceId(accessToken, "noSignupExp", "control", DeviceId);
//        driver.terminateApp("in.mohalla.sharechat");
//        driver.activateApp("in.mohalla.sharechat");
    //    driver.resetApp();
//        new DeviceHelper(driver).waitInSec(3);
//        launchApplication("9", "RZ8M62S64ED", "0.0.0.0:4752", "8252");
       // getPerformanceData();
        System.out.println("Before class in Test Setup is executed");
    }

    @Parameters({"DeviceName"})
    @BeforeMethod(alwaysRun = true) //Initiate this if you have to launch the appium once for all set of tests
    public void beforeMethod(@Optional String DeviceName, Method name) throws IOException, InterruptedException {
        //launchApplication(name, myDeviceContext, getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1));
        System.out.println("==================================Before Method TestSetup=======================");
        startTest(name, DeviceName);
    }


    @AfterMethod(alwaysRun = true) //Initiate those if you have to launch the appium once for all set of tests
    public void afterMethod(ITestResult result) throws IOException {
        System.out.println("==================================After Method TestSetup=======================");
        testResultCapture(result);
        //closeApplication();
    }


    @AfterClass(alwaysRun = true)
    public void afterClass() {
        System.out.println("==================================After Class TestSetup=======================");

        closeApplication();

    }

    @Parameters({"Devices"})
    @AfterSuite(alwaysRun = true)
    public void afterSuite(@Optional String devices) throws Exception {
        System.out.println("After Suite");
        flushReport(devices);
    }

}
