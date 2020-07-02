package com.flipkart.pages.actions;

import com.flipkart.config.ContextManager;
import com.flipkart.config.DeviceHelper;
import com.flipkart.pages.objects.HomeObjects;
import com.flipkart.pages.objects.LoginObjects;
import com.flipkart.pages.objects.SearchResultObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class HomeActions {
    AppiumDriver<MobileElement> driver;
    public DeviceHelper deviceHelper() {return new DeviceHelper(driver);}

    LoginObjects loginObjects = new LoginObjects();
    HomeObjects homeObjects = new HomeObjects();
    SearchResultObjects searchResultObjects = new SearchResultObjects();

    public HomeActions() {
        this.driver = ContextManager.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        PageFactory.initElements(new AppiumFieldDecorator(driver), loginObjects);
        PageFactory.initElements(new AppiumFieldDecorator(driver), homeObjects);
        PageFactory.initElements(new AppiumFieldDecorator(driver), searchResultObjects);
    }

    public void tapOnBackButtonInMyAccount() {
        deviceHelper().waitTillTheElementIsVisible(homeObjects.myAccountScreen);
        homeObjects.backButtonInMyAccount.click();
        deviceHelper().reportLogging("Tap on back button in My Account screen");
    }

    public void enterTextInSearchField(String searchText) {
        deviceHelper().waitTillTheElementIsVisible(homeObjects.searchBoxInHomeScreen);
        homeObjects.searchBoxInHomeScreen.click();
        deviceHelper().reportLogging("Tap on search box in home screen");
        homeObjects.searchField.sendKeys(searchText);
        deviceHelper().reportLogging("Enter text: "+searchText+" in search field");
    }

    public void selectFirstItemInSearchSuggestions() {
        deviceHelper().waitTillTheElementIsVisible(homeObjects.searchSuggestionsList.get(0));
        homeObjects.searchSuggestionsList.get(0).click();
        deviceHelper().reportLogging("Tap on first item in search suggestion screen");
    }

    public void tapOnFilterButton() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.filterButton);
        searchResultObjects.filterButton.click();
        deviceHelper().reportLogging("Tap on Filter button in search result screen");
    }

    public void tapOnBrandButtonInFilters() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.brandButtonInFilterScreen);
        searchResultObjects.brandButtonInFilterScreen.click();
        deviceHelper().reportLogging("Tap on brand button in filter screen");
    }

    public void scrollToBrandsAndSelect(String brandName) {
        int scrollCount = 20;
        while (scrollCount > 0) {
            if (deviceHelper().isElementPresentUsingTextXpath(brandName)) {
                deviceHelper().generateTextXpathAndReturnElement(brandName).click();
                break;
            } else {
                deviceHelper().scrollUpWithInElement(searchResultObjects.brandsList, 100);
            }
        }
    }

    public void tapOnApplyButton() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.applyButton);
        searchResultObjects.applyButton.click();
        deviceHelper().reportLogging("Tap on Apply button in search result screen");
    }

    public void tapOnSortButton() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.sortButton);
        searchResultObjects.sortButton.click();
        deviceHelper().reportLogging("Tap on sort button in search result screen");
    }

    public void tapOnLowToHighRadioButton() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.lowToHighButton);
        searchResultObjects.lowToHighButton.click();
        deviceHelper().reportLogging("Tap on Price Low to High radio button");
    }

    public void selectFirstItemInSearchResult(String brandName) {
        List<MobileElement> itemsList =  driver.findElements(By.xpath("//android.widget.TextView[contains(@text, '"+brandName+"')]"));
        if (!deviceHelper().isElementPresent(searchResultObjects.unavailableItem)){
            itemsList.get(0).click();
        } else {
            for (MobileElement item: itemsList) {
                if (!item.getText().equalsIgnoreCase(searchResultObjects.unavailableItemName.getText())) {
                    item.click();
                    break;
                }
            }
        }
        deviceHelper().reportLogging("Tap on first item appearing on screen");
    }

    public int getItemPrice() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.itemPrice);
        String itemPrice = searchResultObjects.itemPrice.getText().replaceAll("₹", "").replaceAll(",", "").trim();
        return Integer.parseInt(itemPrice);
    }

    public void tapOnAddToCardButton() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.addToCartButton);
        searchResultObjects.addToCartButton.click();
        deviceHelper().reportLogging("Tap on Add to Cart button");
    }

    public void tapOnSkipAndGoToCard() {
        if (deviceHelper().isElementPresent(searchResultObjects.skipGoToCardButton)) {
            searchResultObjects.skipGoToCardButton.click();
            deviceHelper().reportLogging("Tap on Skip & Go To Cart button");
        }
    }

    public void validateToThePrice(int itemPrice) {
        deviceHelper().scrollToMobileElement(searchResultObjects.totalAmount, "3");
        int itemPriceAmount = Integer.parseInt(searchResultObjects.priceValue.getText().replaceAll("₹", "").replaceAll(",", "").trim());
        String deliveryFee = searchResultObjects.deliveryFee.getText().replaceAll("₹", "").replaceAll(",", "").trim();
        int totalAmount = Integer.parseInt(searchResultObjects.totalAmount.getText().replaceAll("₹", "").replaceAll(",", "").trim());
        if (deliveryFee.equalsIgnoreCase("FREE")) {
            deliveryFee = "0";
        }
        Assert.assertEquals(itemPriceAmount, itemPrice);
        int sumOfPrices = itemPriceAmount+Integer.valueOf(deliveryFee);
        Assert.assertEquals(sumOfPrices, totalAmount);
        int finalPrice = Integer.parseInt(searchResultObjects.finalAmount.getText().replaceAll("₹", "").replaceAll(",", "").trim());
        Assert.assertEquals(finalPrice, totalAmount);
    }

    public void removeItemInCart() {
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.removeItemButton);
        searchResultObjects.removeItemButton.click();
        deviceHelper().reportLogging("Tap on remove item button");
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.removeItemButton);
        searchResultObjects.removeItemButton.click();
        deviceHelper().reportLogging("Tap on remove button on confirmation popup");
        deviceHelper().waitTillTheElementIsVisible(searchResultObjects.yourCartIsEnmpty);
    }
}
