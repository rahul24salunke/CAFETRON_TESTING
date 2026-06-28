package com.cafetron.tests.cart;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.flows.CartCheckoutFlow;
import com.cafetron.pages.CartPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class CartTests extends AuthenticatedBaseTest {

    @Test(description = "TC-018 to TC-023: Verify cart summary, persistence, and navigation state",
            groups = {"regression", "integration", "uat"})
    public void shouldShowCartState() {
        CartPage cartPage = new CartPage(getDriver());
        cartPage.open();

        Assert.assertTrue(cartPage.isDisplayed(), "Cart page should be visible");
        Assert.assertTrue(cartPage.hasCartState(), "Cart should show items, summary, or empty state");
        Assert.assertTrue(cartPage.isBackToMenuAvailable(), "Cart should allow returning to menu");
    }

    @Test(description = "TC-024 to TC-030: Verify cart checkout path when an item is available",
            groups = {"smoke", "regression", "integration", "uat"})
    public void shouldEnableCheckoutWhenItemCanBeAdded() {
        try {
            new CartCheckoutFlow(getDriver()).openCheckoutWithCartItem();
        } catch (SkipException skip) {
            throw skip;
        }

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.isDisplayed(), "Cart should be visible after adding an item");
        Assert.assertTrue(cartPage.isCheckoutAvailable(), "Checkout button should be available with cart item");
    }
}
