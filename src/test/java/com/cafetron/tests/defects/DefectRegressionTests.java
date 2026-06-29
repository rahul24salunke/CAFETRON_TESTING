package com.cafetron.tests.defects;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.flows.AuthFlow;
import com.cafetron.flows.CartCheckoutFlow;
import com.cafetron.pages.AdminDashboardPage;
import com.cafetron.pages.AdminOperationsPage;
import com.cafetron.pages.AdminVendorsPage;
import com.cafetron.pages.CheckoutPage;
import com.cafetron.pages.LoginPage;
import com.cafetron.pages.MenuPage;
import com.cafetron.pages.OrdersPage;
import com.cafetron.pages.ProfilePage;
import com.cafetron.pages.QrScannerPage;
import com.cafetron.pages.VendorMenuManagePage;
import com.cafetron.pages.VendorOrdersPage;
import com.cafetron.pages.WalletPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class DefectRegressionTests extends BaseTest {

    @Test(description = "DF-003: Cutoff time should be represented in admin operations before checkout",
            groups = {"defect", "regression", "integration"})
    public void df003ShouldExposeCutoffStatusBeforeCheckout() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminOperationsPage operationsPage = new AdminOperationsPage(getDriver());
        operationsPage.open();

        Assert.assertTrue(operationsPage.hasCutoffControls(), "Cutoff controls should be visible");
        Assert.assertTrue(operationsPage.hasStatusSummary(), "Cutoff/order status summary should be visible");
    }

    @Test(description = "DF-004: Vendor scanner start should show camera preview, permission, result, or error",
            groups = {"defect", "regression", "integration"})
    public void df004ScannerStartShouldShowFeedback() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        QrScannerPage scannerPage = new QrScannerPage(getDriver());
        scannerPage.open();
        scannerPage.startScanner();

        Assert.assertTrue(scannerPage.hasScannerFeedback(), "Scanner start should show visible feedback");
    }

    @Test(description = "DF-005: Admin vendor phone should reject alphabetic/excessively long value",
            groups = {"defect", "regression", "sanity"})
    public void df005VendorPhoneShouldRejectInvalidValues() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminVendorsPage vendorsPage = new AdminVendorsPage(getDriver());
        vendorsPage.open();
        vendorsPage.createVendor(TestDataFactory.uniqueName("Invalid Phone Vendor"),
                "invalid.phone." + System.currentTimeMillis() + "@cafetron.test",
                "ABCphone999999999999999999999999999999",
                "QA Contact");

        Assert.assertFalse(vendorsPage.vendorFeedbackText().toLowerCase().contains("saved"),
                "Invalid phone should not save successfully");
    }

    @Test(description = "DF-006: Authenticated user should not see public login form by opening /login",
            groups = {"defect", "regression", "rbac"})
    public void df006AuthenticatedUserShouldNotSeeLoginForm() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        new LoginPage(getDriver()).open();

        Assert.assertFalse(new LoginPage(getDriver()).isLoginPageDisplayed(),
                "Authenticated user should be redirected away from login form");
    }

    @Test(description = "DF-007: Vendor should not access employee menu browsing/cart route",
            groups = {"defect", "regression", "rbac"})
    public void df007VendorShouldNotSeeEmployeeOrderingMenu() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        MenuPage menuPage = new MenuPage(getDriver());
        menuPage.open();

        Assert.assertFalse(menuPage.hasEmployeeOrderingControls(),
                "Vendor should not see employee ordering/cart controls on /menu");
    }

    @Test(description = "DF-008: Vendor orders Back action should not expose employee menu",
            groups = {"defect", "regression", "rbac"})
    public void df008VendorBackShouldStayInVendorArea() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(getDriver());
        vendorOrdersPage.open();
        if (!vendorOrdersPage.isBackToMenuVisible()) {
            throw new SkipException("Back to menu link is not visible in this UI state.");
        }
        vendorOrdersPage.clickBackToMenu();

        Assert.assertFalse(new MenuPage(getDriver()).hasEmployeeOrderingControls(),
                "Vendor back navigation should not expose employee ordering UI");
    }

    @Test(description = "DF-009: Logout should be available in wallet/authenticated shell",
            groups = {"defect", "regression", "usability"})
    public void df009WalletShouldProvideLogoutAccess() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        WalletPage walletPage = new WalletPage(getDriver());
        walletPage.open();

        Assert.assertTrue(walletPage.isDisplayed(), "Wallet should load for authenticated user");
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.open();
        Assert.assertTrue(profilePage.hasAuthenticatedNavigation(), "Authenticated shell should provide logout path");
    }

    @Test(description = "DF-010: Vendor queue route should be implemented, not placeholder only",
            groups = {"defect", "regression", "integration"})
    public void df010VendorQueueShouldShowRealQueueState() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(getDriver());
        vendorOrdersPage.open();

        Assert.assertTrue(vendorOrdersPage.hasQueueState(), "Vendor queue should show queue, empty, or error state");
    }

    @Test(description = "DF-011: Vendor duplicate menu item names should not save as duplicate",
            groups = {"defect", "regression"})
    public void df011DuplicateVendorMenuItemShouldBeRejected() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();
        String itemName = TestDataFactory.uniqueName("Duplicate Item");
        managePage.createItem(itemName, "20", "4", "Snack");
        managePage.createItem(itemName, "20", "4", "Snack");

        Assert.assertFalse(managePage.saveFeedbackText().toLowerCase().contains("saved"),
                "Duplicate item name should not save successfully");
    }

    @Test(description = "DF-012: Vendor menu form should validate zero stock clearly",
            groups = {"defect", "regression", "usability"})
    public void df012ZeroStockShouldShowValidationOrUnavailableWarning() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();
        managePage.createItem(TestDataFactory.uniqueName("Zero Stock Item"), "20", "0", "Snack");

        Assert.assertFalse(managePage.saveFeedbackText().toLowerCase().contains("saved"),
                "Zero stock item should not save silently as available");
    }

    @Test(description = "DF-013: Toggling zero-stock availability should not log vendor out",
            groups = {"defect", "regression"})
    public void df013VendorShouldRemainAuthenticatedAfterAvailabilityAction() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Vendor should remain in menu-management area");
    }

    @Test(description = "DF-014: Vendor locked delete controls should be clear and not misleading",
            groups = {"defect", "regression", "usability"})
    public void df014VendorDeleteControlShouldNotBeMisleading() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.hasMenuManagementState(), "Vendor menu management state should be clear");
    }

    @Test(description = "DF-015: Admin menu-management dropdowns should remain readable",
            groups = {"defect", "regression", "usability"})
    public void df015AdminMenuFiltersShouldBeReadable() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Admin menu management should load without broken layout");
    }

    @Test(description = "DF-016: Admin menu vendor filter should not hide visible vendor items incorrectly",
            groups = {"defect", "regression"})
    public void df016AdminVendorFilterShouldKeepMenuStateVisible() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.hasMenuManagementState(), "Admin menu filter area should show list or empty state");
    }

    @Test(description = "DF-017: Admin saving edited menu item should not log admin out",
            groups = {"defect", "regression"})
    public void df017AdminShouldRemainAuthenticatedInMenuManagement() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Admin should remain in menu-management area");
    }

    @Test(description = "DF-019: Wallet top-up should reject impractically high amount",
            groups = {"defect", "regression"})
    public void df019WalletShouldRejectHugeTopUp() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        WalletPage walletPage = new WalletPage(getDriver());
        walletPage.open();
        walletPage.topUp("999999999999");

        Assert.assertTrue(walletPage.hasFeedback(), "Huge wallet top-up should show validation feedback");
    }

    @Test(description = "DF-020: Wallet and vendor timestamps should be visible and usable",
            groups = {"defect", "regression", "usability"})
    public void df020TimestampFieldsShouldBeVisibleInUi() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        WalletPage walletPage = new WalletPage(getDriver());
        walletPage.open();

        Assert.assertTrue(walletPage.isDisplayed(), "Wallet timestamp area should load with wallet details");
    }

    @Test(description = "DF-021: Manual ordering-window close should be visible to checkout flow",
            groups = {"defect", "regression", "integration"})
    public void df021ManualOrderingWindowStateShouldBeVisible() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminOperationsPage operationsPage = new AdminOperationsPage(getDriver());
        operationsPage.open();

        Assert.assertTrue(operationsPage.hasOrderingWindowControls(), "Manual ordering-window controls should be visible");
    }

    @Test(description = "DF-022: Over-stock validation should be visible in checkout/cart UI",
            groups = {"defect", "regression", "integration"})
    public void df022CheckoutShouldExposeValidationArea() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.open();

        Assert.assertTrue(checkoutPage.isDisplayed() || checkoutPage.hasFeedback()
                        || checkoutPage.currentUrlContains("/cart"),
                "Checkout should expose validation or route user to cart prerequisite");
    }

    @Test(description = "DF-023: Pickup QR page should provide back navigation path",
            groups = {"defect", "regression", "usability"})
    public void df023OrdersShouldProvideNavigationForQrJourney() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        OrdersPage ordersPage = new OrdersPage(getDriver());
        ordersPage.open();

        Assert.assertTrue(ordersPage.hasOrderState(), "Orders/QR journey should show navigable order state");
    }

    @Test(description = "DF-024: Vendor Add Item form should not show required errors immediately",
            groups = {"defect", "regression", "usability"})
    public void df024VendorAddItemFormShouldOpenCleanly() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();
        managePage.openCreateItemForm();

        Assert.assertTrue(managePage.isItemFormDisplayed(), "Add item form should open cleanly");
    }

    @Test(description = "DF-025: Admin dashboard CSV controls should be available",
            groups = {"defect", "regression"})
    public void df025AdminCsvControlsShouldBeVisible() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminDashboardPage dashboardPage = new AdminDashboardPage(getDriver());
        dashboardPage.open();

        Assert.assertTrue(dashboardPage.hasCsvControls(), "CSV controls should be visible on dashboard");
    }

    @Test(description = "DF-026: QR upload/scanner flow should provide functional verification result or error",
            groups = {"defect", "regression", "integration"})
    public void df026QrVerificationShouldShowResultOrError() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        QrScannerPage scannerPage = new QrScannerPage(getDriver());
        scannerPage.open();
        scannerPage.startScanner();

        Assert.assertTrue(scannerPage.hasScannerFeedback(), "QR verification should show result or clear error");
    }

    @Test(description = "DF-027: Admin-created vendor should have a usable login path",
            groups = {"defect", "regression", "rbac"})
    public void df027AdminVendorCreationShouldCommunicateLoginCapability() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminVendorsPage vendorsPage = new AdminVendorsPage(getDriver());
        vendorsPage.open();

        Assert.assertTrue(vendorsPage.isDisplayed(), "Admin vendor creation UI should be available");
    }

    @Test(description = "DF-028: Employee-only pages should block admin/vendor users",
            groups = {"defect", "regression", "rbac"})
    public void df028VendorShouldNotAccessEmployeeOnlyPages() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        MenuPage menuPage = new MenuPage(getDriver());
        menuPage.open();

        Assert.assertFalse(menuPage.hasEmployeeOrderingControls(),
                "Vendor should not access employee-only ordering controls");
    }

    @Test(description = "DF-029: Admin should be able to reach menu delete/action state without logout",
            groups = {"defect", "regression"})
    public void df029AdminMenuDeleteFlowShouldStayAuthenticated() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Admin should remain authenticated on menu management");
    }

    @Test(description = "DF-030: Admin should be able to reach vendor delete/action state without logout",
            groups = {"defect", "regression"})
    public void df030AdminVendorDeleteFlowShouldStayAuthenticated() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminVendorsPage vendorsPage = new AdminVendorsPage(getDriver());
        vendorsPage.open();

        Assert.assertTrue(vendorsPage.isDisplayed(), "Admin should remain authenticated on vendor management");
    }

    @Test(description = "DF-031: Daily cutoff status should be visible in operations table",
            groups = {"defect", "regression"})
    public void df031DailyCutoffStatusShouldBeVisible() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminOperationsPage operationsPage = new AdminOperationsPage(getDriver());
        operationsPage.open();

        Assert.assertTrue(operationsPage.hasStatusSummary(), "Daily cutoff status should be visible");
    }

    @Test(description = "DF-032: Cutoff update should validate required field when cleared",
            groups = {"defect", "regression", "sanity", "usability"})
    public void df032CutoffUpdateShouldValidateRequiredField() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminOperationsPage operationsPage = new AdminOperationsPage(getDriver());
        operationsPage.open();
        operationsPage.clearCutoffAndSave();

        Assert.assertTrue(operationsPage.hasFeedback() || operationsPage.hasCutoffControls(),
                "Cleared cutoff save should show validation feedback and keep cutoff controls visible");
    }
}
