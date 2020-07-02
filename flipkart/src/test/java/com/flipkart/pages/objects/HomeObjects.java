package com.flipkart.pages.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import java.util.List;

public class HomeObjects {

    @AndroidFindBy(id = "fk-cp-accounts")
    public MobileElement myAccountScreen;

    @AndroidFindBy(className = "android.widget.ImageButton")
    public MobileElement backButtonInMyAccount;

    @AndroidFindBy(id = "com.flipkart.android:id/search_widget_textbox")
    public MobileElement searchBoxInHomeScreen;

    @AndroidFindBy(id = "com.flipkart.android:id/search_autoCompleteTextView")
    public MobileElement searchField;

    @AndroidFindBys({@AndroidBy(id = "com.flipkart.android:id/root_titles")})
    public List<MobileElement> searchSuggestionsList;


}
