package com.cafetron.flows;

import com.cafetron.data.Role;
import com.cafetron.pages.VendorOrdersPage;
import org.openqa.selenium.WebDriver;

public class OrderFulfillmentFlow {
    private final WebDriver driver;

    public OrderFulfillmentFlow(WebDriver driver) {
        this.driver = driver;
    }

    public boolean canOpenVendorQueue() {
        new AuthFlow(driver).loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(driver);
        vendorOrdersPage.open();
        return vendorOrdersPage.isDisplayed() && vendorOrdersPage.hasQueueState();
    }
}
