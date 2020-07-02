package com.flipkart.pages.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginObjects {

    @AndroidFindBy(xpath = "//*[@resource-id='com.flipkart.android:id/locale_icon_layout']")
    public MobileElement langEnglishButton;

    @AndroidFindBy(id = "com.flipkart.android:id/select_btn")
    public MobileElement continueInEnglish;

    @AndroidFindBy(id = "com.flipkart.android:id/phone_input")
    public MobileElement phoneNumberField;

    @AndroidFindBy(id = "com.flipkart.android:id/tv_right_cta")
    public MobileElement useEmailLink;

    @AndroidFindBy(id = "com.flipkart.android:id/phone_input")
    public MobileElement emailIdField;

    @AndroidFindBy(id = "com.flipkart.android:id/button")
    public MobileElement continueButton;

    @AndroidFindBy(id = "com.flipkart.android:id/phone_input")
    public MobileElement passwordField;

    @AndroidFindBy(id = "com.flipkart.android:id/sub_title")
    public MobileElement subTitleInLoginScreen;

}
