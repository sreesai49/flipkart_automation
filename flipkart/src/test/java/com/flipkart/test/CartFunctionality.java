package com.flipkart.test;

import com.flipkart.pages.actions.HomeActions;
import com.flipkart.pages.actions.LoginActions;
import com.flipkart.utils.TestSetUp;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartFunctionality extends TestSetUp {

    String emailId = "ssaaii4499@gmail.com";
    String password = "Sai@9491";
    public LoginActions loginActions() {return new LoginActions();}
    public HomeActions homeActions() {return new HomeActions();}

    @BeforeClass(dependsOnMethods = {"beforeClass"}, alwaysRun = true)
    public void loginFunctionality() {
        loginActions().selectEnglish();
        loginActions().tapOnUseEmailId();
        loginActions().enterEmailId(emailId);
        loginActions().tapOnContinueButton();
        loginActions().enterPassword(password);
        loginActions().tapOnContinueButton();
//        homeActions().tapOnBackButtonInMyAccount();
    }

    /**
     *
     */
    @Test(enabled = true, groups = {"RegressionTest", "CartFunctionality"}, alwaysRun = true)
    public void verifyItemFunctionalityInCartScreen() {
        String brandName = "Apple";
        homeActions().enterTextInSearchField("Mobiles");
        homeActions().selectFirstItemInSearchSuggestions();
        homeActions().tapOnFilterButton();
        homeActions().tapOnBrandButtonInFilters();
        homeActions().scrollToBrandsAndSelect(brandName);
        homeActions().tapOnApplyButton();
        homeActions().tapOnSortButton();
        homeActions().tapOnLowToHighRadioButton();
        homeActions().selectFirstItemInSearchResult(brandName);
        int itemPrice = homeActions().getItemPrice();
        homeActions().tapOnAddToCardButton();
        homeActions().tapOnSkipAndGoToCard();
        homeActions().validateToThePrice(itemPrice);
        homeActions().removeItemInCart();
    }
}
