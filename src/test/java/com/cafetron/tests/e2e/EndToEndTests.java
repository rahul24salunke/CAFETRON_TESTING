package com.cafetron.tests.e2e;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.flows.CartCheckoutFlow;
import com.cafetron.flows.OrderFulfillmentFlow;
import com.cafetron.pages.CartPage;
import com.cafetron.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class EndToEndTests extends AuthenticatedBaseTest {

    @Test(description = "TC-075: Employee order to vendor accept supporting flow",
            groups = {"e2e", "integration", "uat"})
    public void shouldSupportEmployeeCartToCheckoutFlow() {
        try {
            new CartCheckoutFlow(getDriver()).openCheckoutWithCartItem();
        } catch (SkipException skip) {
            throw skip;
        }

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.isCheckoutAvailable(), "Employee cart should be ready for checkout");

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.open();
        Assert.assertTrue(checkoutPage.isDisplayed(), "Checkout should open for the employee order flow");
    }

    @Test(description = "TC-076: Employee order to vendor decline/refund supporting flow",
            groups = {"e2e", "integration", "uat"})
    public void shouldSupportVendorQueueForOrderFulfillment() {
        Assert.assertTrue(new OrderFulfillmentFlow(getDriver()).canOpenVendorQueue(),
                "Vendor queue should be accessible for fulfillment flow");
    }
}
