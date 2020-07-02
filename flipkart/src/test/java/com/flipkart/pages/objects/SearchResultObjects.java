package com.flipkart.pages.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SearchResultObjects {

    @AndroidFindBy(xpath = "//*[@text='Filter']")
    public MobileElement filterButton;

    @AndroidFindBy(xpath = "//*[@text='Brand']")
    public MobileElement brandButtonInFilterScreen;

    @AndroidFindBy(xpath = "//*[@text='Search Brand']")
    public MobileElement searchBranchField;

    @AndroidFindBy(xpath = "(//android.widget.ScrollView)[1]")
    public MobileElement brandsList;

    @AndroidFindBy(xpath = "//*[@text='Apply']")
    public MobileElement applyButton;

    @AndroidFindBy(xpath = "//*[@text='Sort']")
    public MobileElement sortButton;

    @AndroidFindBy(xpath = "//*[@text='Price -- Low to High']")
    public MobileElement lowToHighButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, '₹')]")
    public MobileElement itemPrice;

    @AndroidFindBy(xpath = "//*[@text='ADD TO CART']")
    public MobileElement addToCartButton;

    @AndroidFindBy(xpath = "//*[@text='SKIP & GO TO CART']")
    public MobileElement skipGoToCardButton;

    @AndroidFindBy(xpath = "//*[@text='PRICE DETAILS ']//following::android.widget.TextView[contains(@text, '₹')]")
    public MobileElement priceValue;

    @AndroidFindBy(xpath = "//*[@text='Delivery Fee ']//following::android.widget.TextView")
    public MobileElement deliveryFee;

    @AndroidFindBy(xpath = "//*[@text='Total Amount ']//following::android.widget.TextView[contains(@text, '₹')]")
    public MobileElement totalAmount;

    @AndroidFindBy(xpath = "//*[@text='Place Order ']//preceding::android.widget.TextView[contains(@text, '₹')]")
    public MobileElement finalAmount;

    @AndroidFindBy(xpath = "//*[@text='Temporarily Unavailable']")
    public MobileElement unavailableItem;

    @AndroidFindBy(xpath = "//*[@text='Temporarily Unavailable']//following::android.widget.TextView")
    public MobileElement unavailableItemName;

    @AndroidFindBy(xpath = "//*[@text='Remove ']")
    public MobileElement removeItemButton;

    @AndroidFindBy(xpath = "//*[@text='Your cart is empty! ']")
    public MobileElement yourCartIsEnmpty;

}
