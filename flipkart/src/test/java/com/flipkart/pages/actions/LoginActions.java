package com.flipkart.pages.actions;

import com.flipkart.config.ContextManager;
import com.flipkart.config.DeviceHelper;
import com.flipkart.pages.objects.LoginObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginActions {
    AppiumDriver<MobileElement> driver;
    public DeviceHelper deviceHelper() {return new DeviceHelper(driver);}

    LoginObjects loginObjects = new LoginObjects();

    public LoginActions() {
        this.driver = ContextManager.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        PageFactory.initElements(new AppiumFieldDecorator(driver), loginObjects);
    }

    public void selectEnglish() {
        deviceHelper().waitTillTheElementIsVisible(loginObjects.langEnglishButton);
        loginObjects.langEnglishButton.click();
        deviceHelper().reportLogging("Tap on English language button in Welcome screen");
        loginObjects.continueInEnglish.click();
        deviceHelper().reportLogging("Tap on Conitnue to English button");
    }

    public void tapOnUseEmailId() {
        deviceHelper().waitTillTheElementIsVisible(loginObjects.useEmailLink);
        loginObjects.useEmailLink.click();
        deviceHelper().reportLogging("Tap on user email ID link in login screen");
    }

    public void enterEmailId(String email) {
        deviceHelper().waitTillTextToBePresentInElement(loginObjects.emailIdField, "Email ID");
        loginObjects.emailIdField.sendKeys(email);
        deviceHelper().reportLogging("Entered "+email+" in email ID field");
    }

    public void tapOnContinueButton() {
        deviceHelper().waitTillTheElementIsVisible(loginObjects.continueButton);
        loginObjects.continueButton.click();
        deviceHelper().reportLogging("Tap on Continue button");
    }

    public void enterPassword(String password) {
        deviceHelper().waitTillTextToBePresentInElement(loginObjects.emailIdField, "Password");
        loginObjects.emailIdField.sendKeys(password);
        deviceHelper().reportLogging("Entered "+password+" in Password field");
    }
}
