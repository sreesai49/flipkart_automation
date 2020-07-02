package com.flipkart.config;

import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Qualitrix Technologies Pvt Ltd.
 *
 * @author: Ajith Manjunath
 * Date:		07/17/2018
 * @UpdatedBy: Deepak P
 * Purpose:     To implement the test driven through the extent test
 */

public class ExtentTestFactory {
    private static ThreadLocal<ExtentTest> extentPool = new ThreadLocal<ExtentTest>();

    public static ExtentTest getExtentTest() {
        return extentPool.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        extentPool.set(extentTest);
    }

    static String strAbsolutepath = new File("").getAbsolutePath();
    public static int month;
    public static int day;
    public static int year;
    public static String[] monthName = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-hh.mm.ss";

    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        Random rand = new Random();
        int num = rand.nextInt(1000);
        Date dt = new Date();
        return sdf.format(dt);
    }

    public static String getPath() {

        String strPath;
        String osPathTillDate;
        Calendar cal = Calendar.getInstance();
        int iMonth = cal.get(Calendar.MONTH);
        String sMonthName = monthName[iMonth];
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String sDate = sdf.format(cal.getTime());
        osPathTillDate = sMonthName + "/" + sDate;
        String time = now();
        strPath = osPathTillDate + "/" + "Report_" + time + ".html";

        return strPath;
    }

}






