package com.flipkart.config;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

/**
 * Created by Qualitrix Technologies Pvt Ltd.
 *
 * @author: Ajith Manjunath
 * Date:		07/17/2018
 * Purpose:	    Test Utilities and Listeners
 * @Updated By : Deepak P
 */

public class WebDriverListener extends TestSetUpFactory implements ITestListener {

    public static ExtentTestFactory extentTestFactory;


    public WebDriverListener() {
        extentTestFactory = new ExtentTestFactory();
    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

        Iterator<ITestResult> skippedTestCases = iTestContext.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (iTestContext.getSkippedTests().getResults(method).size() > 0) {
                System.out.println("Removing:" + skippedTestCase.getTestClass().toString());
                skippedTestCases.remove();
            }
        }


    }

    @Override
    public void onTestStart(ITestResult iTestResult) {


    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        if (iTestResult.getThrowable().getMessage().contains("Skipped By retry")) {
            ExtentTestFactory.getExtentTest().getExtent().removeTest(ExtentTestFactory.getExtentTest());
        } else {

            StringWriter exceptionInfo = new StringWriter();
            iTestResult.getThrowable().printStackTrace(new PrintWriter(exceptionInfo));

            String methodClassName = iTestResult.getThrowable().getMessage();
            for (StackTraceElement stack : iTestResult.getThrowable().getStackTrace()) {
                if (stack.getClassName().contains("co.sharechat.pages.Actions")) {
                    methodClassName = methodClassName + "   Failed in Class: " + stack.getClassName() +
                            ",  in Method : " + stack.getMethodName() +
                            ",  and Line : " + stack.getLineNumber() + " This test case will be rerun, Since it failed in the first attempt";
                    break;
                }
            }

            ExtentTestFactory.getExtentTest().skip(methodClassName);
            ExtentTestFactory.getExtentTest().addScreenCaptureFromBase64String(takeScreenShot());

        }
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }
}