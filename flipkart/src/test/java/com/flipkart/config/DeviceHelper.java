package com.flipkart.config;

import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;
import io.appium.java_client.*;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.clipboard.HasClipboard;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;



public class DeviceHelper {

    public AppiumDriver driver;
    WebDriverWait wait = null;
    int counter = 0;

    public DeviceHelper(AppiumDriver driver) {
        this.driver = driver;
    }


    public void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * 0.89);
        int endy = (int) (size.height * 0.2);
        int startx = (int) (size.width / 2.2);
        try {
            waitInSec(2);
            System.out.println("Trying to swipe up from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction(driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(point(startx, endy)).release().perform();
            reportLogging("Swipe up");
        } catch (Exception e) {
            System.out.println("Swipe action was not successful.");
        }
    }

    public void waitTillTheElementIsVisibleAndClickable(MobileElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitTillListHasElements(List<MobileElement> list) {
        for (byte i = 1; list.size() == 0 && i <= 20; i++)
            waitInSec(1);
    }

    public boolean waitTillTheElementInVisible(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForPageToLoad(WebElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public void waitForElementState(WebElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.stalenessOf(id));

        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public void waitForPageToLoad(List<WebElement> id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfAllElements(id));
    }

    public void waitForElementToDisAppear(List<WebElement> id, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.invisibilityOfAllElements(id));
    }

    public MobileElement waitForElementToAppear(MobileElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(id));
        return id;
    }

    public WebElement waitForElementToAppear(WebElement id, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(id));
        return id;
    }

    public WebElement waitForElement(WebElement arg) {
        waitForPageToLoad(arg);
        WebElement el = arg;
        return el;
    }

    public void WaitForFrameAndSwitchToIt(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }

    public void WaitForFrameAndSwitchToIt(int id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }

    public void ScrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForElements(List<WebElement> arg) {
        waitForPageToLoad(arg);
    }

    public MobileElement waitForElementToAppearOnScreen(MobileElement arg) {
        waitForElementToAppear(arg);
        MobileElement el = arg;
        return el;
    }

    public void clickUntilElementExists(WebElement clickLocator, By by) {
        boolean elementOnScreen;
        int i = 0;
        do {
            if (i == 25) {
                break;
            }
            try {
                driver.findElement(by);
                break;
            } catch (NoSuchElementException e) {
                clickLocator.click();
                elementOnScreen = false;
                System.out.println(i);
            }
            i++;
        } while (!elementOnScreen);
    }

    public String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

//    public void logStepIntoExtentReport(String elementDescription, String action,
//                                        String typeString) {
//        ExtentTestManager.getTest().log(Status.INFO,
//                elementDescription + "; " + withBoldHTML("Text") + ": " + typeString);
//    }

    public String withBoldHTML(String string) {
        if (!string.trim().isEmpty()) {
            return "<b>" + string + "</b>";
        } else {
            return "";
        }
    }

//    public String getPageObjectElemetDescription(Object pageObject, String fieldName) {
//        try {
//            return this.getClass().getAnnotation(PageName.class).value() + "::" +
//                    pageObject.getClass().getField(fieldName).getAnnotation(ElementDescription.class)
//                            .value();
//        } catch (NoSuchFieldException e) {
//
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * This Function is to check the element is present or not
     *
     * @author Ramesh
     * @param: Mobile Element
     */
    public boolean isElementDisplayed(MobileElement locator) {
        try {
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }

    public String getCurrentMonth(int month) {
        int i = Calendar.getInstance().get(Calendar.MONTH);
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month + i];
    }

    public void refreshWebPage() {
        driver.navigate().refresh();
    }

    public void switchToNewWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    /**
     * Swipe the screen on the basis of x,y Co-ordinates. Start from (x1,y1) to (x2, Y2)
     * <p>
     * param x1
     * param y1
     *
     * @param x2
     * @param y2
     */
    public void swipe(int x1, int y1, int x2, int y2) {
        new TouchAction(((AppiumDriver<MobileElement>) driver)).press(PointOption.point(x1, y1))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(x2, y2))
                .release().perform();
    }

    /**
     * This Function will pause the execution for given secs.
     *
     * @param secs : No of seconds to be paused.
     */
    public void waitInSec(int secs) {
        try {
            Thread.sleep(secs * 1000);
            //reportLogging("Using sleep for " + secs + " seconds");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function will search for the element till(Maximum)
     * value of 'explicit_timeout' in Config.
     *
     * @param locator : Pass the By Locator
     * @return : MobileElement
     */
    public MobileElement getElement(By locator) {
        if (wait == null)
            wait = new WebDriverWait(((AppiumDriver<MobileElement>) driver), 50);
        return (MobileElement) wait
                .until(ExpectedConditions.visibilityOf(((AppiumDriver<MobileElement>) driver).findElement(locator)));
    }


    /**
     * This method will click on the Home button
     *
     */
    public void clickHomeBtn() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));

    }

    /**
     * This method will open the notifications on device.
     *
     */
    public void openNotification() {
        ((AndroidDriver) driver).openNotifications();
    }

    /**
     * This method will return the copied text in the clipboard of device.
     *
     * @author Jasmeet
     * @since 11 July 2019
     */
    public String getCopiedText() {
        return ((HasClipboard) driver).getClipboardText();
    }


    /**
     * generateRandomPhNo(int length) can return the string of digits as specified length. Maximum 15 digit number could be generated.
     *
     * @param length NO of digits in the number
     * @return String of digits as specified length\
     */
    public String generateRandomPhNo(int length) {
        if (length > 15)
            length = 15;
        return Long.toString((int) ((Math.random() * 100000000)) + 999999999999999l).substring(15 - length, 15);
    }

    /**
     * generateRandomName() can generate the random name.
     *
     * @return name
     */
    public String generateRandomName() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        return name;
    }


    /**
     * @param locator : By Locator of the element
     * @return true if element is displayed. Otherwise false.
     * @author
     */
    public boolean isDisplayed(MobileElement locator) {


        return locator.isDisplayed();
    }

    /**
     * This Function is to Wait till element visible
     *
     * @param: Mobile Element & String
     */
    public void waitTillTheElementIsVisible(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitTillTextToBePresentInElement(MobileElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /**
     * This Function is to Wait till element visible
     *
     * @param: Mobile Element & String
     */
    public void waitTillTheElementIsVisible(MobileElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This Function is to enter text in text field using action class
     *
     * @param: Mobile Element & String
     */
    public void writeInputActions(MobileElement element, String otp) {
        try {
            waitTillTheElementIsVisible(element);
            Actions a = new Actions(driver);
            a.sendKeys(otp).build().perform();
        } catch (Exception e) {
            System.out.println("Write Input Action failed");
        }
    }

    /**
     * This function will longPress on the MobileElement provided
     *
     * @param element : MobileElement, int Seconds
     * @return : void
     */

    public void longPress(MobileElement element, int seconds) {
        try {

            new TouchAction(driver).
                    longPress(longPressOptions().withElement(element(element)).withDuration(Duration.ofSeconds(seconds))).release().perform();

        } catch (Exception e) {
            System.out.println("LongPress failed");
            System.out.println(e.getMessage());
        }
    }

    /** Tap the screen on the basis of x1,y1 point.
     * @param x1
     * @param y1
     * @since 10 july
     */
    public void tapOnPoint(int x1, int y1) {
        new TouchAction((AppiumDriver<MobileElement>) driver).tap(PointOption.point(x1, y1)).perform();
    }

    /**
     * This Function is to Scroll to element
     *
     * @param: Mobile Element & String
     */
    public void scrollToMobileElement(MobileElement element, String scrollCount) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeUp();
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }


    /**
     * This Function is to Scroll to element
     *
     * @param: Mobile Element & String
     */
    public void scrollToMobileElementInProfileScreen(MobileElement element, String scrollCount) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    Dimension size = driver.manage().window().getSize();
                    int starty = (int) (size.height * 0.89);
                    int endy = (int) (size.height * 0.5);
                    int startx = (int) (size.width / 2.2);
                    System.out.println("Trying to swipe up from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
                    new TouchAction(driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(2)))
                            .moveTo(point(startx, endy)).release().perform();
                    reportLogging("Swipe up");
                }
            }if(!isElementDisplayed(element)){
                swipeDown();
                scrollToMobileElementInProfileScreen(element,scrollCount);
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }


    /**
     * This Function is to Scroll to element from top to bottom
     *
     * @param: Mobile Element & String
     */
    public void swipeDownToMobileElement(MobileElement element, String scrollcount) {
        try {
            waitInSec(2);
            int count = Integer.parseInt(scrollcount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeDown(0.3, 0.70);
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * This Function is to Scroll to element from top to bottom
     *
     * @author Ramesh
     * @param: Mobile Element & String
     */
    public void swipeDownToMobileElement(String value, String scrollcount) {
        try {
            waitInSec(2);
            int count = Integer.parseInt(scrollcount);
            for (int i = 0; i < count; i++) {
                if (generateTextXpathIsElementDisplay(value)) {
                    break;
                } else {
                    swipeDown(0.3, 0.70);
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }


    /**
     * This Function is to Scroll to element from top to bottom
     *
     * @author Ramesh
     * @param: Mobile Element & String
     */
    public void swipeUpToMobileElement(MobileElement element, String scrollCount) {
        try {
            waitInSec(2);
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeUp(0.55, 0.2);
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    public void scrollDownToMobileElement(MobileElement element, String scrollCount) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {

                if (isElementPresent(element)) {
                    break;
                } else {
                    swipeDown();
                }

            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * This Function is to Scroll to element
     *
     * @author Diljeet
     * @param: Mobile Element & String
     */
    public void scrollToMobileElement1(MobileElement element, MobileElement element1, String scrollCount) {
        try {
            if (isElementPresent(element)) {
                waitInSec(2);
                int count = Integer.parseInt(scrollCount);
                for (int i = 0; i < count; i++) {
                    if (isElementDisplayed(element1)) {
                        isElementDisplayed(element1);
                    } else {
                        swipeUp();
                    }

                }
            } else {
                driver.resetApp();
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * This method will click on the Back button.
     *
     * @author Jasmeet
     * @since 15 July 2019
     */
    public void clickBackBtn() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
    }

    /**
     * This method will return the Width of the Phone Screen
     *
     * @author Jasmeet
     * @since 08 July 2019
     */
    public int getWidthOfScreen() {
        return driver.manage().window().getSize().width;
    }

    /**
     * This method will return the Height of the Phone Screen
     *
     * @author Jasmeet
     * @since 08 July 2019
     */
    public int getHeightOfScreen() {
        return driver.manage().window().getSize().height;
    }

    public void startActivity(String appPackage, String appActivity) {
        ((AndroidDriver) driver).startActivity(new Activity(appPackage, appActivity));

    }

    /**
     * This Function is to report log's in execution reports
     *
     * @author Ramesh
     * @param: String
     */
    public void reportLogging(String info) {
        try {
            ExtentTest loggerReport = ExtentTestFactory.getExtentTest();
            loggerReport.info(info);
        } catch (NullPointerException e) {
            reportClassLogging(info);
        }
    }

    /**
     * This Function is to report log's in execution reports
     *
     * @author Ramesh
     * @param: String
     */
    public void reportClassLogging(String info) {
        ExtentTest loggerReport = ExtentTestFactoryParent.getExtentTest();
        loggerReport.info(info);
    }

    /**
     * This Function is to check the element is present or not after explicit waiting of the element.
     *
     * @author Jasmeet
     * @param: Mobile Element
     */
    public boolean isElementPresent(MobileElement locator) {
        try {
            waitForElementToAppear(locator);
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (Exception e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }

    /**
     * This Function is to check the element is present or not after explicit waiting of the element.
     *
     * @author Jasmeet
     * @param: Mobile Element
     */
    public boolean isElementPresent(MobileElement locator, int secs) {
        try {
            waitForElementToAppear(locator, secs);
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (Exception e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }
    /**
     * This Function is to Select Language
     *
     * @author Ramesh
     * @param: String
     */
    public boolean selectLanguageUsingText(String value) {
        try {
            waitInSec(2);
            String lang = driver.findElement(By.xpath("//*[@text='" + value + "']")).getText();
            driver.findElement(By.xpath("//*[@text='" + value + "']")).click();
            boolean flag = value.contentEquals(lang);
            reportClassLogging("Selected language is " + lang + " langauge passed is " + value + " and the flag is " + flag);
            // Assert.assertEquals(lang, value, "Selected language is not same as language passed");
            //reportLogging(value + " Language is selected");
            return flag;
        } catch (Exception e) {
            //reportLogging("Language is not selected");
            return false;
        }
    }


    public void swipeDownUsingCount(int howManySwipes) {
        Dimension size = driver.manage().window().getSize();
        // calculate coordinates for vertical swipe
        int startVerticalY = (int) (size.height * 0.8);
        int endVerticalY = (int) (size.height * 0.21);
        int startVerticalX = (int) (size.width / 2.1);
        try {
            for (int i = 1; i <= howManySwipes; i++) {
                new TouchAction<>(driver).press(point(startVerticalX, endVerticalY))
                        .waitAction(waitOptions(ofSeconds(2))).moveTo(point(startVerticalX, startVerticalY))
                        .release().perform();
                reportLogging("Swipe Down/Refresh the page");
            }
        } catch (Exception e) {
            System.out.println("Swipe action was not successful.");
        }
    }

    /**
     * This Function is to swipe Down or Refresh the screen
     *
     * @author Ramesh
     */
    public void swipeUp(double startYValue, double endYValue) {
        Dimension size = driver.manage().window().getSize();
//        int startY = (int) (size.height * startYValue);
//        int endY = (int) (size.height * endYValue);
//        int startX = (int) (size.width / 2.2);
        int starty = (int) (size.height * startYValue);
        int endy = (int) (size.height * endYValue);
        int startx = (int) (size.width / 2.2);
        try {
            System.out.println("Trying to swipe up from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction(driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(point(startx, endy)).release().perform();
            reportLogging("Swipe up");
//            System.out.println("Trying to swipe up from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
//            new TouchAction((PerformsTouchActions) driver).press(point(startX, startY)).waitAction(waitOptions(ofSeconds(3)))
//                    .moveTo(point(startX, endY)).release().perform();
//            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            System.out.println("Swipe did not complete successfully.");
        }
    }

    /**
     * This Function is to swipe Down or Refresh the screen
     *
     * @author Ramesh
     */
    public void swipeDown(double startYValue, double endYValue) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * startYValue);
        int endY = (int) (size.height * endYValue);
        int startX = (int) (size.width / 2.2);
        try {
            System.out.println("Trying to swipe up from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
            new TouchAction((PerformsTouchActions) driver).press(point(startX, startY)).waitAction(waitOptions(ofSeconds(3)))
                    .moveTo(point(startX, endY)).release().perform();
            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            System.out.println("Swipe did not complete successfully.");
        }
    }

    /**
     * This Function is to swipe Down or Refresh the screen
     *
     * @author Ramesh
     */
    public void swipeDown(double height) {
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * height);
        int endy = (int) (size.height * 0.90);
        int startx = (int) (size.width / 2.2);
        try {
            System.out.println("Trying to swipe down from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction((PerformsTouchActions) driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(3)))
                    .moveTo(point(startx, endy)).release().perform();
            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            System.out.println("Swipe did not complete successfully.");
        }
    }


    /**
     * This Function is to swipe Down or Refresh the screen
     *
     * @author Ramesh
     */
    public void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * 0.45);
        int endy = (int) (size.height * 0.90);
        int startx = (int) (size.width / 2.2);
        try {
            System.out.println("Trying to swipe down from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction((PerformsTouchActions) driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(3)))
                    .moveTo(point(startx, endy)).release().perform();
            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            System.out.println("Swipe did not complete successfully.");
        }
    }

    /**
     * This Function is to swipe Right
     *
     * @author Ramesh
     */
    public void swipeRightTopLine() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.2);
        int startX = (int) (size.width * 0.90);
        int endX = (int) (size.width * 0.05);
        try {
            new TouchAction((PerformsTouchActions) driver).press(point(startX, startY)).waitAction(waitOptions(ofSeconds(3)))
                    .moveTo(point(endX, startY)).release().perform();
            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            reportLogging("Swipe did not complete successfully.");
        }
    }

    /**
     * This Function is to Scroll left horizontal to element
     *
     * @author Ramesh
     * @param: Mobile Element & String
     */
    public void swipeHorizontalTopLine(MobileElement element, String scrollCount) {
        try {
            waitInSec(2);
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeRightTopLine();
                }

            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * This Function is to check element is not present and return true or false
     *
     * @author Ramesh
     * @param: Mobile Element
     */
    public boolean isElementNotPresent(MobileElement element) {
        if (isElementDisplayed(element) == false) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     * @author Ramesh
     * @param: Mobile Element
     */
    public void isElementPresentAssertTrue(MobileElement element) {
        try {
//            waitForElementToAppear(element);-----Please dont use wait in this assert
            Assert.assertTrue(isElementDisplayed(element));
        } catch (Exception e) {
            reportLogging(element + " The Element is not found, Assertion failed");
        }
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     * @author Ramesh
     * @param: Mobile Element
     */
    public boolean isElementNotPresentAssertTrue(MobileElement element) {
        Boolean flag = false;
        try {
           flag  = isElementNotPresent(element);
            Assert.assertTrue(isElementNotPresent(element));
        } catch (Exception e) {
            reportLogging(element + " The Element present, Assertion failed");
        }

        return flag;
    }

    /**
     * This Function is to scroll to element text
     *
     * @author Ramesh
     * @param: String text
     */
//    public WebElement scrollToAndroidElementByText(String text) {
    public void scrollToAndroidElementByText(String text) {
        generateXpathAndScrollToElement("*", text, 20);
//        return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
//                ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
    }

    /**
     * This Function is to hide keyboard
     *
     * @author Ramesh
     */
    public void hideKeyBoard() {
        try {
            ((AppiumDriver<MobileElement>) driver).hideKeyboard();
            reportLogging("Hide KeyBoard");
        } catch (Exception e) {
            reportLogging("KeyBoard not found to hide");
        }
    }

    /**
     * This Function is Pressing enter key
     *
     * @author Jasmeet
     */
    public void enterKey() {
        try {
            //((AndroidDriver) driver).pressKey(AndroidKeyCode.KEYCODE_SEARCH);
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } catch (Exception e) {
            System.out.println("Hide keyboard failed");
        }
    }

    /**
     * This Function is Pressing enter key
     *
     * @author Ramesh
     */
    public void searchKey() {
        try {
            //((AndroidDriver) driver).pressKey(AndroidKeyCode.KEYCODE_SEARCH);
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.SEARCH));
        } catch (Exception e) {
            System.out.println("Hide keyboard failed");
        }
    }

    public void scrollUsingTouchActions_ByElements(MobileElement startElement, MobileElement endElement) {
        TouchAction actions = new TouchAction(driver);
        actions.press(element(startElement)).waitAction(waitOptions(Duration.ofSeconds(1))).moveTo(element(endElement)).release().perform();
    }

    /**
     * This Function is to Scroll  bottom to top And click the element
     *
     * @author Deepak
     * @param: Mobile Element and count of scroll
     */
    public void scrollToTextAndTapElement(String text, int scrollcount) {
        try {
            generateTextXpathAndScrollToElementText(text, scrollcount);
            //         scrollToAndroidElementByText(text);
            MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + text + "']"));
            element.click();
        } catch (Exception e) {
        }
    }

    /**
     * This Function is to Scroll  bottom to top And click the element
     *
     * @author Deepak
     * @param: Mobile Element and count of scroll
     */
    public void scrollToTextAndTapElement(String className, String text, int scrollcount) throws NoSuchElementException {
        try {
            generateTextXpathAndScrollToElementText(text, scrollcount);
            //         scrollToAndroidElementByText(text);
            MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//" + className + "[@text='" + text + "']"));
            element.click();
        } catch (Exception e) {
        }
    }

    /**
     * This Function is to Scroll to element from top to bottom
     *
     * @author Ramesh
     * @param: Mobile Element & String
     */
    public void scrollToMobileElementTopToBottom(MobileElement element, String scrollcount) {
        try {
            waitInSec(2);
            int count = Integer.parseInt(scrollcount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeDown();

                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }

    }

    /**
     * This Function is to generateXpath using text and return text
     *
     * @return MobileElement
     * @author Ramesh
     * @param: String
     */
    public MobileElement generateTextXpathAndReturnElement(String value) {
        MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[contains(@text,'" + value + "')]"));
        return element;
    }

    /**
     * @author deepakpedagada
     * @version 1.0
     * @since 03/06/2020
     */
    public void scrollDownToText(String text) throws NoSuchElementException {
        MobileElement element = null;
        int counter = 0;
        do{
        try {
            element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + text + "']"));
            scrollDownToMobileElement(element, "30");
        } catch (NoSuchElementException e) {
            counter++;
            swipeDown();
            element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + text + "']"));
            scrollDownToMobileElement(element, "30");
        }catch (Exception e) {
            counter++;
            swipeDown();
            element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + text + "']"));
            scrollDownToMobileElement(element, "30");
        }
        finally {
            counter++;
            swipeDown();
            element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + text + "']"));
            scrollDownToMobileElement(element, "30");
        }}while (counter<10);
        scrollDownToMobileElement(element, "30");

    }

    /**
     * This Function is to generateXpath using text and return text
     *
     * @return MobileElement
     * @author Ramesh
     * @param: String
     */
    public MobileElement generateTextXpathAndReturnCommentElement(String value) {
        MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']//following::*[@resource-id='in.mohalla.sharechat:id/tv_post_comment']"));
        return element;
    }

    /**
     * This Function is to generateXpath using text and return List of Elements
     *
     * @return List of Elements
     * @author Deepak
     * @param: String
     */
    public List<MobileElement> generateTextXpathAndReturnElements(String value) {
        List<MobileElement> elements = ((AppiumDriver<MobileElement>) driver).findElements(By.xpath("//*[contains(@text,'" + value + "')]"));
        return elements;
    }

    /**
     * This Function is to Tap android back button
     *
     * @author Ramesh
     */
    public void tapAndroidBackButton() {
        try {
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
            reportLogging("Click default back button on android device");
        } catch (Exception e) {
            System.out.println("Android Back click failed");
        }
    }


    /**
     * This function is to add fail info to validation tests
     *
     * @param info
     * @author : Deepak Pedagada
     */

    public void reportLoggingFail(String info) {
        ExtentTest loggerReport = ExtentTestFactory.getExtentTest();
        loggerReport.fail(info);

    }

    /**
     * This Function is to generateXpath using text and Check element is present using assert
     *
     * @author Ramesh
     * @param: String
     */
    public boolean generateTextXpathIsElementPresent(String value) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//*[contains(@text,'" + value + "')]"));
        if (elements.size() > 0) {
            flag = true;
            reportLogging("Check the " + value + " element is present");
        }
        Assert.assertTrue(flag, "Element is not present");
        return flag;
    }

    /**
     * This Function is to generateXpath using text and Check element is present using assert
     *
     * @author Ramesh
     * @param: String
     */
    public boolean generateTextXpathIsElementPresent(String className, String value) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//" + className + "[contains(@text, '" + value + "')]"));
        if (elements.size() > 0) {
            flag = true;
            reportLogging("Check the " + value + " element is present");
        }
//        Assert.assertTrue(flag, "Element is not present");
        return flag;
    }


    /**
     * This Function is to generateXpath starts-with using text and Check element is present using assert
     *
     * @author Ramesh
     * @param: String
     */
    public boolean generateTextXpathStartsWithIsElementPresent(String value) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//*[starts-with(@text,'" + value + "')]"));
        if (elements.size() > 0) {
            flag = true;
            reportLogging("Check the " + value + " element is present");
        }
//        Assert.assertTrue(flag, "Element is not present");
        return flag;
    }


    //img[starts-with(@alt,'Goo')]

    /**
     * This Function is to generateXpath using text and Check element is present using assert
     *
     * @author Ramesh
     * @param: String
     */
    public boolean generateTextXpathIsElementDisplay(String value) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//*[contains(@text,'" + value + "')]"));
        if (elements.size() > 0) {
            flag = true;
            reportLogging("Check the " + value + " element is present");
        }
        return flag;
    }


    /**
     * This function will scroll the posts till it finds a post with comments/Likes in comment/Likes section
     *
     * @param element1, element2, scrollcount
     * @return : void
     * @author : Deepak Pedagada
     */

    public void scrollToCount(MobileElement element, MobileElement element1, MobileElement element2, String scrollcount) {

        int count = Integer.parseInt(scrollcount);
        try {
            if (isElementPresent(element))
                for (int i = 0; i < count; i++) {
                    if (isElementPresent(element1) && isElementPresent(element2)) {
                        System.out.println("Text is " + element2.getText());
                        boolean flag = element2.getText().contains("K");
                        boolean flag1 = element2.getText().contains("M");
                        System.out.println("Count flag is " + !flag);
                        if (isElementPresent(element2) && !flag && !flag1) {
                            // element1.click();
                            break;
                        } else {
                            swipeUp();
                        }
                    } else {
                        swipeUp();
                    }
                }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }


    /**
     * This function will wait for the element to be visible and clickable and then clicks on it
     *
     * @param element
     * @author : Deepak Pedagada
     */

    public void waitAndClick(MobileElement element) {
        waitTillTheElementIsVisibleAndClickable(element);
        element.click();


//        List<MobileElement> elementList = driver.findElements(By.id("com.whatsapp:id/contact_row_container"));
//            elementList.get(1).click();
    }


    /**
     * This function is to log report agit adnd to be used in case of validations in a test case.
     *
     * @param flag     return a boolean if the test case meets or does not meet the validation condition
     * @param info     Pass the validation info when expected condition met
     * @param failInfo Pass the fail validation info when expected condition is not met
     * @author: Deepak Pedagada
     */
    public void reportLoggingForValidation(boolean flag, String info, String failInfo) {
        ExtentTest loggerReport = ExtentTestFactory.getExtentTest();
        if (flag) {
            loggerReport.info(info);
            Assert.assertTrue(flag, failInfo);

        } else {
            loggerReport.fail(failInfo);

        }
    }

    public String getText(MobileElement element) {
        String elementText = element.getText();
        return elementText;
    }

    public void doubleTap(MobileElement mobileElement) {

        try {
            new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).perform().release();
        } catch (Exception e) {

        }
    }

    public void doubleTapOnImagePost(MobileElement mobileElement) {


        try {
            new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).perform().release();
//            new TouchAction<>(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1)))
//                    .tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1))).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1))).perform().release();

            //  new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(5).tap(waitOptions(Duration.ofMillis(10))).withElement(element(mobileElement)).withTapsCount(5).perform().release();

//            new TouchAction(driver).press(TapOptions.tapOptions().withElement(element(mobileElement)).waitAction(Duration.ofMillis(100)).press(100,300).release().perform();

//            new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(6).withTapsCount(5)).perform().release();
//            new TouchAction(driver).longPress(longPressOptions().withElement(element(element)).withDuration(Duration.ofSeconds(value))).release().perform();
//            TouchActions action = new TouchActions(driver);
//            action.doubleTap(mobileElement);
//            action.perform();


//            new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(5)).perform().release();
        } catch (Exception e) {

        }
    }

    public void doubleTapOnScreen() {

        try {
            Dimension size = driver.manage().window().getSize();
            int startY = (int) (size.height * 0.25);
            int startX = (int) (size.width * 0.25);
            new TouchAction(driver).tap(TapOptions.tapOptions().withPosition(PointOption.point(startX, startY)).withTapsCount(2)).tap(TapOptions.tapOptions().withPosition(point(startX, startY)).withTapsCount(2)).perform().release();
        } catch (Exception e) {

        }
    }

    public void singleTap(MobileElement mobileElement) {
        new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).perform().release();
    }

    public void singleTapOnce(MobileElement mobileElement) {
        new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(1)).perform().release();
    }


    /**
     * Perform swipe action vertically from point X to point Y on any screen
     *
     * @author :
     * @since : 22-July-2019
     */
    public void swipeHorizontal(double HXS, double HXE, double ANCHOR) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * HXS);
        int endX = (int) (size.width * HXE);
        int Anchor = (int) (size.height * ANCHOR);
        try {
            System.out.println("Trying to swipe up from x:" + startX + " y:" + Anchor + ", to x:" + endX + " y:" + Anchor);
            new TouchAction((PerformsTouchActions) driver).press(point(startX, Anchor)).waitAction(waitOptions(ofSeconds(4)))
                    .moveTo(point(endX, Anchor)).release().perform();
            reportLogging("Swiped Horizontally");
        } catch (Exception e) {
            System.out.println("Swipe failed");
        }
    }


    public int getCount(MobileElement element) {

        String count = getText(element);

        return Integer.parseInt(count);

    }

    /**
     * @author Ramesh
     * @version 1.0
     */
    public void tapOnGiveString(String className, String value) {
        waitInSec(2);
        driver.findElement(By.xpath("//" + className + "[@text='" + value + "']")).click();
    }

    /**
     * @author Suresh Kutagulla
     * @version 1.0
     */
    public void verifyGivenString(String className, String value) {
        waitInSec(2);
        try {
            driver.findElement(By.xpath("//" + className + "[@text='" + value + "']"));
        } catch (Exception e) {
            reportLoggingFail("Given string element is not found");
        }
    }


    /**
     * Perform generate xpath and scroll to element
     * ns
     *
     * @author :Ramesh
     * @since : 2-September-2019
     */
    public void generateXpathAndScrollToElement(String className, String value, int scrollCount) {
//        try {
        waitInSec(2);
        for (int i = 0; i < scrollCount; i++) {
            MobileElement element = (AndroidElement) driver.findElement(By.xpath("//" + className + "[@text='" + value + "']"));
            if (isElementDisplayed(element)) {
                break;
            } else {
                swipeUp();
            }
        }
//        } catch (Exception e) {
//            System.out.println("Scroll to mobile element failed");
//        }
    }

    /**
     * This Function is to tap on middle of the screen
     *
     * @author Ramesh
     */
    public void tapOnMiddleOfScreen() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.35);
        int startX = (int) (size.width * 0.35);
        tapOnPoint(startX, startY);
        reportLogging("Tap on middle of the screen");
    }

    /**
     * This Function is to scroll to element text
     *
     * @author Ramesh
     * @param: Element text
     */
    public void scrollToAndroidElementText(String text) {
        int count = 30;
        for (int i = 0; i < count; i++) {
            try {
                driver.findElement(By.xpath("//*[contains(@text,'" + text + "')]"));
                reportLogging("displayed expected text element in screen");
                break;
            } catch (Exception e) {
                swipeUp();
            }

            if (i == 28)
                Assert.fail("Expected text element is not displayed in screen");


        }
    }

    /**
     * This Function is list of element present or not
     *
     * @author Ramesh
     */
    public boolean listOfElementsPresents(List<MobileElement> elements) {
        boolean Flag = false;
        int countOfBuckets = elements.size();
        if (countOfBuckets > 0) {
            Flag = true;
        }
        return Flag;
    }

    /**
     * Perform generate xpath and click the element
     *
     * @author :Ramesh
     * @since : 15-November-2019
     */
    public void generateXpathAndClickElement(String className,String value) {
        try {
            waitInSec(2);
            MobileElement element = (AndroidElement) driver.findElement(By.xpath("//"+className+"[contains(@text,'" + value + "')]"));
            element.click();
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * Perform generate xpath and click the element
     *
     * @author :Suresh Kutagulla
     * @since : 06-FEB-2020
     */
    public void generateXpathAndClickSameElementSecondText(String value) {
        try {
            waitInSec(2);
            MobileElement element = (AndroidElement) driver.findElement(By.xpath("(//*[contains(@text,'" + value + "')])[2]"));
            element.click();
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }


    /**
     * Perform swipe action vertically from point X to point Y on any screen
     *
     * @author :Ramessh
     * @since : 22-July-2019
     */
    public void swipeUp(double heightValue) {
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * heightValue);
        int endy = (int) (size.height * 0.2);
        int startx = (int) (size.width / 2.2);
        try {
            System.out.println("Tnrying to swipe up from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction((PerformsTouchActions) driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(point(startx, endy)).release().perform();
            reportLogging("Swipe up");
        } catch (Exception e) {
            System.out.println("Swipe action was not successful.");
        }
    }

    /**
     * This Function is to Scroll to element
     *
     * @author Ramesh
     * @param: Mobile Element & String
     */
    public void scrollToMobileElement(MobileElement element, String scrollCount, double heightValue) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeUp(heightValue);
                }

            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * This Function is to check the element is enabled or not
     *
     * @author Ramesh
     * @param: Mobile Element
     */
    public boolean isElementEnabled(MobileElement locator) {
        if (locator.isEnabled()) {
            System.out.println("Element present on screen ***********" + locator);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will Wait maximum 20 secs for element.
     *
     * @author : Ramesh
     * @param: Mobile Element
     * @since : 28-11-2019
     */
    public void waitTillElementPresent(MobileElement element) {
        for (int i = 1; i < 20; i++) {
            if (isElementDisplayed(element) == false) {
                waitInSec(1);
            } else {
                break;
            }
        }
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     * @param: String
     */
    public void isElementNotPresentUsingTextXpath(String value) {
        try {
            MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
            Assert.assertFalse(element.isDisplayed());
        } catch (Exception e) {
            reportLogging(value + " The Element is not present, Assertion pass");
        }
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     * @param: String
     */
    public void isElementNotPresentUsingTextXpath(String value, int scrollCount) {
        while (scrollCount > 0){
            try {
                MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
                Assert.assertFalse(element.isDisplayed());
            } catch (Exception e) {
                swipeUp();
                reportLogging(value + " The Element is not present. Assertion pass");
            }
            scrollCount--;
        }
    }

    /**
     * This method will drag And Drop the element.
     *
     * @param: Mobile Element
     * @since : 28-11-2019
     */
    public void dragAndDrop(MobileElement element1) {

        Dimension size = driver.manage().window().getSize();
        //int starty = (int) (size.height * 0.2);
        int endy = (int) (size.height * 0.2);
        //int startx = (int) (size.width / 2.2);
        int endx = (int) (size.width / 3);
        //Using Action class for drag and drop.
        Actions act = new Actions(driver);
        //Dragged and dropped.
        act.dragAndDropBy(element1, endx, endy).build().perform();
        // act.dragAndDrop(element1, element2).build().perform();
    }


    /**
     * This method will drag And Drop the element.
     *
     * @author : Ramesh
     * @param: Mobile Element
     * @since : 28-11-2019
     */
    public void dragAndDrop(MobileElement element1,MobileElement element2) {
        Dimension size = driver.manage().window().getSize();
        //int starty = (int) (size.height * 0.2);
        int endy = (int) (size.height * 0.2);
        //int startx = (int) (size.width / 2.2);
        int endx = (int) (size.width / 3);
        //Using Action class for drag and drop.
        Actions act = new Actions(driver);
        //Dragged and dropped.
        act.dragAndDropBy(element1, endx, endy).moveToElement(element2).build().perform();


        //Perform drag and drop operation using TouchAction class.
        //Created object of TouchAction class.
//        TouchAction action = new TouchAction((MobileDriver) driver);
//        System.out.println("It Is dragging element.");
//        //It will hold tap on 3rd element and move to 6th position and then release tap.
//        action.longPress(element(element1)).moveTo(element(element2)).release().perform();
//        System.out.println("It Is dragging element2");
    }

    public void doubleTap(MobileElement mobileElement, int countOfTaps) {
        try {
            new TouchAction(driver).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(countOfTaps)).tap(TapOptions.tapOptions().withElement(element(mobileElement)).withTapsCount(2)).perform().release();
        } catch (Exception e) {

        }
    }

    public void scrollVideoPreviewToIntegerCountOfElement(MobileElement mobileElement, int scrollCount) {
//        try {
        for (int i = 0; i < scrollCount; i++) {
            String likeCount = mobileElement.getText();
            System.out.println("likeCount text is " + likeCount);
            boolean flagK = likeCount.contains("K");
            boolean flagM = likeCount.contains("M");
            if (flagK == true) {
                swipeUp();
            } else if (flagM == true) {
                swipeUp();
            } else {
                break;
            }
        }
//             } catch (Exception e) {
//
//        }
    }

    /**
     * This Function is to generateXpath and scroll to text element
     *
     * @return MobileElement
     * @param: String
     */
    public boolean generateTextXpathAndScrollToElementText(String value, int count) {
        boolean status = false;
//        int counter = 0;
//        MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
        try {
            for (int i = 0; i < count; i++) {
                MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
                System.out.println("element=" + element);
//                boolean flag = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']")).isDisplayed();
                boolean flag = element.isDisplayed();
                System.out.println("flag=" + flag);
                if (flag == true) {
                    status = true;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            counter++;
            if (counter < 10) {
                swipeUp();
                generateTextXpathAndScrollToElementText(value, count);
            }
        }
        return status;
    }

    /**
     * This Function is to generateXpath and scroll to text element
     *
     * @return MobileElement
     * @param: String
     */
    public boolean generateTextXpathAndScrollWithElementToText(MobileElement element, String textToFind, int count) {
        boolean status = false;
//        int counter = 0;
//        MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
        try {
            for (int i = 0; i < count; i++) {
                MobileElement textElement = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + textToFind + "']"));
                System.out.println("element=" + element);
//                boolean flag = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']")).isDisplayed();
                boolean flag = element.isDisplayed();
                System.out.println("flag=" + flag);
                if (flag == true) {
                    status = true;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            counter++;
            if (counter < 10) {
                scrollUpWithInElement(element, 5);
                generateTextXpathAndScrollWithElementToText(element, textToFind, count);
            }
        }
        return status;
    }

    /**
     * This Function is converting string to integer
     *
     * @return int
     * @param: String
     */
    public int convertStringToInteger(String value) {
        int integerValue = Integer.parseInt(value);
        return integerValue;
    }

    /**
     * This Function is enable/disable GPS
     *
     */
    public void toggleGPS() {
        ((AndroidDriver) driver).toggleLocationServices();
    }


    public void doubleTap1(MobileElement element) throws InterruptedException {
        Thread.sleep(5000);
        driver.findElementByAccessibilityId("doubleTap").click();
        MobileElement element1 = (MobileElement) new WebDriverWait(driver, 30).
                until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("doubleTapMe")));
        Thread.sleep(1000);
        Point source = element.getCenter();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(40)));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
        Thread.sleep(4000);
    }

    /**
     * This Function is validating two string
     *
     * @return boolean
     * @param: String
     */
    public boolean verifyTextContains(String expected, String actual) {
        boolean flag = false;
//        if(expected.contentEquals(actual)){
        if (expected.contains(actual)) {
            flag = true;
        }
        return flag;
    }


    /**
     * Perform swipe Left action on Element
     *
     * @since : 04-Feb-2020
     */
    public void swipeLeftOnElement(MobileElement ele0) {
        int startX = ele0.getLocation().getX() + (int) (ele0.getSize().getWidth() * 0.8);
        int startY = ele0.getLocation().getY() + (ele0.getSize().getHeight() / 2);

        int endX = ele0.getLocation().getX() + (int) (ele0.getSize().getWidth() * 0.1);
        int endY = ele0.getLocation().getY() + (ele0.getSize().getHeight() / 2);

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(point(endX, endY))
                .release().perform();

    }

    /**
     * Perform swipe Right action on Element
     *
     * @since : 04-Feb-2020
     */
    public void swipeRightOnElement(MobileElement ele0) {
        int startX = ele0.getLocation().getX() + (int) (ele0.getSize().getWidth() * 0.2);
        int startY = ele0.getLocation().getY() + (ele0.getSize().getHeight() / 2);

        int endX = ele0.getLocation().getX() + (int) (ele0.getSize().getWidth() * 0.8);
        int endY = ele0.getLocation().getY() + (ele0.getSize().getHeight() / 2);

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(point(endX, endY))
                .release().perform();
    }
    /**
     * Perform swipe Right action on Element
     *
     * @since : 16-Jun-2020
     */
    public void swipeUpOnElement(MobileElement ele0) {
        int startX = ele0.getLocation().getX() + (ele0.getSize().getWidth() / 2);
        int startY = ele0.getLocation().getY() + (int) (ele0.getSize().getHeight() * 0.8);

        int endX =ele0.getLocation().getX() + (ele0.getSize().getWidth() / 2);
        int endY = ele0.getLocation().getY() + (int) (ele0.getSize().getHeight() * 0.2);

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(point(endX, endY))
                .release().perform();
    }

    public void setGeoLocation() {
        driver.setLocation(new Location(48.858275, 2.294438, 100));
    }

    /**
     * Perform swipe Right action on Element
     *
     * @since : 04-Feb-2020
     */
    public void swipeByElements(AndroidElement startElement, AndroidElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);

        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);

        new TouchAction(driver)
                .press(point(startX, startY))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(point(endX, endY))
                .release().perform();
    }
    /**
     * This Function to get screen Landscape/Portrait
     *
     * @return ScreenOrientation
     */
    public ScreenOrientation getOrientation() {
        return driver.getOrientation();
    }

    /**
     * This Function is to swipe left to Right
     *
     */
    public void swipeLeftToRight() {
//        Dimension size = driver.manage().window().getSize();
//        int startY = (int) (size.height * 0.5);
//        int startX = (int) (size.width * 0.90);
//        int endX = (int) (size.width * 0.05);

        Dimension size = driver.manage().window().getSize();
        System.out.println(size.height+"height");
        System.out.println(size.width+"width");
        System.out.println(size);
        int startPoint = (int) (size.width * 0.99);
        int endPoint = (int) (size.width * 0.15);
        int ScreenPlace =(int) (size.height*0.40);
        int y=(int)size.height*20;

//        TouchAction ts = new TouchAction(driver);
//        //for(int i=0;i<=3;i++) {
//        ts.press(PointOption.point(startPoint,ScreenPlace ))
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//                .moveTo(PointOption.point(endPoint,ScreenPlace )).release().perform();


        try {
            new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startPoint,ScreenPlace ))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endPoint,ScreenPlace )).release().perform();
//                    press(point(startX, startY)).waitAction(waitOptions(ofSeconds(3)))
//                    .moveTo(point(startY,endX)).release().perform();
            reportLogging("Swipe Down/Refresh the page");
        } catch (Exception e) {
            reportLogging("Swipe did not complete successfully.");
        }
    }

    /**
     * @since : 25-Feb-2020
     */
    public void doubleTapOnElement(MobileElement element) {
        TouchAction taction = new TouchAction(driver);
        taction.tap(tapOptions().withElement(ElementOption.element(element))
                .withTapsCount(2)).perform();
    }

    /**
     * This Function is to Scroll to element UnTill element not visible
     *
     * @param: Mobile Element & String
     */
    public void scrollToMobileElementUnTillElementNotVisible(MobileElement element, String scrollCount) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    swipeUp();

                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

    /**
     * @author sreesairanganadh
     * @since 26/02/2020
     */
    public void scrollOnElementVertically(MobileElement element){
        String bounds = element.getAttribute("bounds");//bounds = [661,754][837,1249]
        String x1Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[1];
        String x2Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[4];
        String y1Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[2];
        String y2Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[5];
        int starty = (Integer.parseInt(y1Value));
        int endy = Integer.parseInt(y2Value);
        int startx = ((Integer.parseInt(x1Value))+(Integer.parseInt(x2Value))) / 2;
        int endY = endy-((endy-starty)/2);
        try {
            new TouchAction(driver).press(point(startx, starty+10)).waitAction(waitOptions(ofSeconds(2)))
                    .moveTo(point(startx, endY)).release().perform();
            reportLogging("Swipe up");
        } catch (Exception e) {
            System.out.println("Swipe action was not successful.");
        }
    }


    /**
     * This Function is to generateXpath using text and Check element is present using assert
     * @param: String
     */
    public boolean getSizeOfElement(String value) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//*[contains(@text,'" + value + "')]"));
        System.out.print("elements.size()==="+elements.size());
        if (elements.size() > 1) {
            flag = true;
            reportLogging("Check the size  of elements is present in current screen");
        }
//        Assert.assertTrue(flag, "Element is not present");
        return flag;
    }


//    public boolean zoomIn(MobileElement element) {
////        List imageParent = (List) getDriver().findElements(By.className(“android.support.v4.view.ViewPager”));
////        WebElement imageChild = imageParent.get(0).findElement(By.className(“android.widget.ImageView”));
//
////        firstScreenShot = element.screenshot(imageChild);
//        int leftX = element.getLocation().getX();
//        int rightX = leftX + element.getSize().getWidth();
//        int upperY = element.getLocation().getY();
//        int lowerY = upperY + element.getSize().getHeight();
//        int middleY = (upperY + lowerY) / 2;
//        int middleX = (leftX + rightX) / 2;
//        TouchAction action0 = new TouchAction(driver).press(point(middleX, middleY)).release().perform();
//        action0.perform();
//        ((AndroidDriver) driver).zoom(middleX, middleY);
//    }

    /**
     * @author sreesairanganadh
     * @since 26/02/2020
     */
    public List<Integer> getBounds(MobileElement element){
        String bounds = element.getAttribute("bounds");//bounds = [661,754][837,1249]
        String x1Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[1];
        String x2Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[4];
        String y1Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[2];
        String y2Value = bounds.replaceAll("[^a-zA-Z0-9]", " ").split(" ")[5];
        int x1 = (Integer.parseInt(x1Value));
        int x2 = Integer.parseInt(x2Value);
        int y1 = (Integer.parseInt(y1Value));
        int y2 = Integer.parseInt(y2Value);
        List<Integer> boundsValues = new ArrayList<>();
        boundsValues.add(x1);
        boundsValues.add(y1);
        boundsValues.add(x2);
        boundsValues.add(y2);
        return boundsValues;
    }


    public void ZoomOut(MobileElement element){
        List<Integer> boundsValue = getBounds(element);
        int x1Value = boundsValue.get(0);
        int y1Value = boundsValue.get(1);
//        int x2Value=boundsValue.get(2);
//        int y2Value=boundsValue.get(3);
        int centerX = element.getCenter().getX();
        int centerY = element.getCenter().getY();
        MultiTouchAction multiTouch = new MultiTouchAction(driver);
        TouchAction tAction0 = new TouchAction(driver);
        TouchAction tAction1 = new TouchAction(driver);
        int midValueX = (centerX-x1Value)/2;
        int midValueY = (centerY-y1Value)/2;
        tAction0.press(point(centerX,centerY)).release();
//        tAction1.press(point(x2Value,y2Value)).moveTo(PointOption.point(x2Value+midValueX,y2Value+midValueY)).release();
        tAction1.press(point(x1Value,y1Value)).moveTo(PointOption.point(x1Value+midValueX,y1Value+midValueY)).release();
        multiTouch.add(tAction0).add(tAction1);
        multiTouch.perform();// now perform both the actions simultaneously (tAction0 and tAction1)
        waitInSec(3);
    }

    public void ZoomIn(MobileElement element){
        List<Integer> boundsValue = getBounds(element);
        int x1Value=boundsValue.get(0);
        int y1Value=boundsValue.get(1);
//        int x2Value=boundsValue.get(2);
//        int y2Value=boundsValue.get(3);

        int centerX=element.getCenter().getX()*2;
        int centerY=element.getCenter().getY()*2;
        MultiTouchAction multiTouch = new MultiTouchAction(driver);
        TouchAction tAction0 = new TouchAction(driver);
        TouchAction tAction1 = new TouchAction(driver);
        int midValueX = (centerX-x1Value)/2;
        int midValueY = (centerY-y1Value)/2;
        tAction0.press(point(centerX,centerY)).release();
        tAction1.press(point(x1Value,y1Value)).moveTo(PointOption.point(midValueX,midValueY)).release().perform();
//        tAction1.press(point(x1Value,y1Value)).moveTo(PointOption.point(x1Value-midValueX,y1Value-midValueY)).release();
//        multiTouch.add(tAction0).add(tAction1).perform();
//        multiTouch.perform();// now perform both the actions simultaneously (tAction0 and tAction1)
        waitInSec(3);
    }

    /**
     * This Function is to generateXpath using String and return MobileElement
     *
     * @return MobileElement
     * @param: String
     */
    public List<MobileElement> generateXpathAndReturnElements(String value) {
        List<MobileElement> elements = ((AppiumDriver<MobileElement>) driver).findElements(By.xpath(value));
        return elements;
    }

    public MobileElement generateXpathAndReturnElement(String value) {
        MobileElement elements = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath(value));
        return elements;
    }
    /**
     * @author sreesairanganadh
     * @param element
     * @param xpath
     * @return MobileElement
     */
    public MobileElement findElementInsideElement(MobileElement element, String xpath){
        try {
            return element.findElement(By.xpath(xpath));
        }catch (Exception e){
            return null;
        }
    }

    public void tapOnOptionsForSelectedMember(String value) {
        MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']/..//following-sibling::android.widget.FrameLayout"));
         element.click();
    }


    public boolean isElementPresentUsingTextXpath(String value) {
        boolean flag = false;
        try {
            MobileElement element = ((AppiumDriver<MobileElement>) driver).findElement(By.xpath("//*[@text='" + value + "']"));
            if (element.isDisplayed()) {
                flag = true;
                return flag;
            } else {
                return flag;
            }
        } catch (Exception e) {
            reportLogging(value + " The Element is not present, so returning false");
            return flag;
        }
    }

    /**
     * @author sreesairanganadh
     * @since 11/03/2020
     * @param element
     * @param latency
     */
    public void swipeHorizantallyRightToLeftWithInElement(MobileElement element, int latency){
        waitTillTheElementIsVisible(element);
        List<Integer> bounds = getBounds(element);
        int x1 = bounds.get(0)+latency;
        int y1 = bounds.get(1);
        int x2 = bounds.get(2)-latency;
        int y2 = bounds.get(3);

        int startY = (int) (y1 + ((y2-y1)*0.4));

//        new TouchAction((PerformsTouchActions) driver).press(point(x2, startY)).waitAction(waitOptions(ofSeconds(4)))
//                .moveTo(point(x1, startY)).release().perform();
        new TouchAction(driver).press(point(x2, startY)).waitAction(waitOptions(ofSeconds(2))).moveTo(point(x1, startY)).release().perform();
        reportLogging("Swipe horizantally with in the element based on");
    }


    /**
     * @author sreesairanganadh
     * @version 1.0
     */
    public void scrollUpWithInElement(MobileElement element, int latency){
        waitTillTheElementIsVisible(element);
        List<Integer> bounds = getBounds(element);
        int x1 = bounds.get(0);
        int y1 = bounds.get(1);
        int x2 = bounds.get(2);
        int y2 = (int) (bounds.get(3) - (bounds.get(3)*0.1));

        int endY = (int) (y1 + ((y2-y1)*0.4));
        int X = x2/2;

//        new TouchAction((PerformsTouchActions) driver).press(point(x2, startY)).waitAction(waitOptions(ofSeconds(4)))
//                .moveTo(point(x1, startY)).release().perform();
        new TouchAction(driver).press(point(X, y2)).waitAction(waitOptions(ofSeconds(2))).moveTo(point(X, endY)).release().perform();
        reportLogging("Swipe horizantally with in the element based on");
    }


    /**
     * This Function is to longPress on middle of the screen
     *
     */
    public void longPressOnMiddleOfScreen(int value) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.35);
        int startX = (int) (size.width * 0.35);
//        new TouchAction(driver).longPress(longPressOptions().withElement(element(element)).withDuration(Duration.ofSeconds(value))).release().perform();
        new TouchAction(driver).longPress(longPressOptions().withPosition(point(startX, startY)).withDuration(Duration.ofSeconds(value))).release().perform();


//        longPress(startX, startY);
//        tapOnPoint(startX, startY);
        reportLogging("Tap on middle of the screen");
    }

    public void longPressOnElement(MobileElement startElement,int xwidth,int ylength) {
        int startX = startElement.getLocation().getX() + (xwidth);
        int startY = startElement.getLocation().getY() + (ylength);
        new TouchAction(driver).longPress(longPressOptions().withPosition(point(startX, startY)).withDuration(Duration.ofSeconds(4))).release().perform();
        reportLogging("Long Press on element");
    }

    /**
     * @author sreesairanganadh
     * @version 1.0
     */
    public void pasteClipBoardContent(MobileElement element){
        waitTillTheElementIsVisible(element);
        element.sendKeys(((AndroidDriver)driver).getClipboardText());
        reportLoggingFail("Pasting copied content in field");
    }

    /**
     * @version 1.0
     */
    public void pasteClipBoardContent1() {
//        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.KEYCODE_CTRL_LEFT, AndroidKeyCode.KEYCODE_V);
//        ((AndroidDriver)driver).pressKeyCode(AndroidKey.CTRL_LEFT,AndroidKey.PASTE);
//        ((AndroidDriver)driver).pressKey(AndroidKey.CTRL_LEFT,AndroidKey.PASTE);
        ((AndroidDriver)driver).pressKeyCode(AndroidKey.CTRL_LEFT.getCode(),AndroidKey.PASTE.getCode());
    }

    /**
     * @version 1.0
     */
    public void passCommandToTerminal(String value) {
        try {
            Process p = Runtime.getRuntime().exec(value);
//           new ProcessBuilder(value).start();
        } catch (Exception e) {

        }
    }

    /**
     * This Function is to Enable or Disable GPS on device
     * User can pass ON or OFF thought parameter If ON GPS will Enable or If OFF GPS will Disable
     * @param: String
     * @Since 16-April-2020
     */
    public void GPS(String value){
        if("OFF".equalsIgnoreCase(value)){
            passCommandToTerminal("adb shell settings put secure location_providers_allowed -gps");
        } else if ("ON".equalsIgnoreCase(value)) {
            passCommandToTerminal("adb shell settings put secure location_providers_allowed +gps");
        }
        reportLoggingFail("Device GPS is "+value);
    }

    public void multipleScrollUp(int count) {
        for (int i = 0; i < count; i++) {
            swipeUp();
        }
    }

}
