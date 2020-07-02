package com.flipkart.config;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


/**
 * Created by Qualitrix Technologies Pvt Ltd.
 *
 * @author: Deepak P
 * Date:		10/17/2019
 * @UpdatedBy: Deepak P
 * Purpose:     To implement the test driven through the extent test
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (counter < retryLimit) {
            counter++;
            result.setThrowable(new RuntimeException("Skipped By retry"));//setStatus(ITestResult.CREATED);
            System.out.println("Retry #" + counter + " for test: " + result.getMethod().getMethodName() + ", on thread: " + Thread.currentThread().getName());
            return true;
        }

        return false;
    }


}
