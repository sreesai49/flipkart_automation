package com.flipkart.config;

import com.relevantcodes.extentreports.ExtentReports;
import io.appium.java_client.AppiumDriver;


public class ContextManager {

    private static ThreadLocal<AppiumDriver> webDriver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return webDriver.get();
    }

    public static void setDriver(AppiumDriver driver) {
        webDriver.set(driver);
    }

    private static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<>();

    public static ExtentReports getExtentReport() {
        return extentReport.get();
    }

    public static void setExtentReports(ExtentReports extent) {
        extentReport.set(extent);
    }

}

