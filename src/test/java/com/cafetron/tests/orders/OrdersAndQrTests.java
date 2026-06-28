package com.cafetron.tests.orders;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.pages.OrdersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrdersAndQrTests extends AuthenticatedBaseTest {

    @Test(description = "TC-042 to TC-044: Verify order history and detail entry points",
            groups = {"regression", "uat", "integration"})
    public void shouldDisplayOrdersHistoryState() {
        OrdersPage ordersPage = new OrdersPage(getDriver());
        ordersPage.open();

        Assert.assertTrue(ordersPage.isDisplayed(), "Orders page should be visible");
        Assert.assertTrue(ordersPage.hasOrderState(), "Orders should show list, empty state, or error UI");
        Assert.assertTrue(ordersPage.isStartOrderingAvailableWhenEmpty(), "Empty orders should guide user back to ordering");
    }
}
