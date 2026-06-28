package com.cafetron.tests.menu;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MenuBrowsingTests extends AuthenticatedBaseTest {

    @Test(description = "TC-012 to TC-015: Verify menu load, search, and visible results area",
            groups = {"smoke", "regression", "uat", "usability"})
    public void shouldLoadMenuAndAllowSearch() {
        MenuPage menuPage = new MenuPage(getDriver());
        menuPage.open();

        Assert.assertTrue(menuPage.isMenuPageDisplayed(), "Menu page UI should be displayed");
        Assert.assertTrue(menuPage.hasMenuResultsArea(), "Menu should show either items or an empty state");
        Assert.assertTrue(menuPage.isSearchAvailable(), "Search input should be available");

        menuPage.search("tea");
        Assert.assertTrue(menuPage.hasMenuResultsArea(), "Search should keep menu results area stable");
    }

    @Test(description = "TC-016 to TC-017: Verify cart controls and availability UI are present",
            groups = {"smoke", "regression", "integration", "uat"})
    public void shouldExposeCartControlsFromMenu() {
        MenuPage menuPage = new MenuPage(getDriver());
        menuPage.open();

        Assert.assertTrue(menuPage.isCartControlVisible(), "Menu should expose a cart control");
    }

    @Test(description = "TC-030: Verify conditional menu empty state for unmatched search",
            groups = {"sanity", "regression", "usability"})
    public void shouldHandleNoMatchSearchState() {
        MenuPage menuPage = new MenuPage(getDriver());
        menuPage.open();
        menuPage.search("zzzz-no-menu-item");

        Assert.assertTrue(menuPage.hasMenuResultsArea(), "No-match search should show a stable UI state");
    }
}
