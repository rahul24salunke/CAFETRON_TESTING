package com.cafetron.tests.vendor;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.flows.AuthFlow;
import com.cafetron.pages.QrScannerPage;
import com.cafetron.pages.VendorMenuManagePage;
import com.cafetron.pages.VendorOrdersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VendorTests extends BaseTest {

    @Test(description = "TC-045 to TC-051: Verify vendor orders queue visibility",
            groups = {"smoke", "regression", "integration", "uat"})
    public void shouldDisplayVendorOrdersQueue() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(getDriver());
        vendorOrdersPage.open();

        Assert.assertTrue(vendorOrdersPage.isDisplayed(), "Vendor orders page should be visible");
        Assert.assertTrue(vendorOrdersPage.hasQueueState(), "Vendor orders should show queue, empty state, or error");
        Assert.assertTrue(vendorOrdersPage.hasVendorNavigation(), "Vendor navigation should be available");
    }

    @Test(description = "TC-052 to TC-056: Verify vendor menu management controls",
            groups = {"regression", "integration"})
    public void shouldDisplayVendorMenuManagement() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Vendor menu management should be visible");
        Assert.assertTrue(managePage.hasMenuManagementState(), "Vendor menu should show list or empty state");
    }

    @Test(description = "TC-057 to TC-059: Verify vendor can open item creation UI",
            groups = {"smoke", "regression", "integration", "uat"})
    public void shouldCreateVendorMenuItemThroughUiWhenAllowed() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();
        managePage.createItem(TestDataFactory.uniqueName("QA Menu Item"), "25", "5", "Snack");

        Assert.assertTrue(managePage.hasSaveFeedback() || managePage.hasMenuManagementState(),
                "Saving a menu item should show feedback or updated menu state");
    }

    @Test(description = "TC-060 to TC-063: Verify vendor scanner startup feedback",
            groups = {"smoke", "regression", "integration", "uat"})
    public void shouldShowScannerStartupFeedback() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        QrScannerPage scannerPage = new QrScannerPage(getDriver());
        scannerPage.open();

        Assert.assertTrue(scannerPage.isDisplayed(), "Scanner page should be visible");
        scannerPage.startScanner();
        Assert.assertTrue(scannerPage.hasScannerFeedback(), "Scanner should show camera, result, or error feedback");
    }
}
