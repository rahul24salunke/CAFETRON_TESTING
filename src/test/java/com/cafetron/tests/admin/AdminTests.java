package com.cafetron.tests.admin;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.flows.AuthFlow;
import com.cafetron.pages.AdminDashboardPage;
import com.cafetron.pages.AdminOperationsPage;
import com.cafetron.pages.AdminVendorsPage;
import com.cafetron.pages.VendorMenuManagePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {

    @Test(description = "TC-064 to TC-066: Verify admin dashboard metrics, tables, and export controls",
            groups = {"smoke", "regression", "uat"})
    public void shouldDisplayAdminDashboard() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminDashboardPage dashboardPage = new AdminDashboardPage(getDriver());
        dashboardPage.open();

        Assert.assertTrue(dashboardPage.isDisplayed(), "Admin dashboard should be visible");
        Assert.assertTrue(dashboardPage.hasKpis(), "Admin dashboard KPIs should be visible");
        Assert.assertTrue(dashboardPage.hasCsvControls(), "Admin dashboard export controls should be visible");
    }

    @Test(description = "TC-067 to TC-071: Verify admin operations ordering window and cutoff controls",
            groups = {"smoke", "regression", "rbac", "uat"})
    public void shouldDisplayAdminOperationsControls() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminOperationsPage operationsPage = new AdminOperationsPage(getDriver());
        operationsPage.open();

        Assert.assertTrue(operationsPage.isDisplayed(), "Admin operations page should be visible");
        Assert.assertTrue(operationsPage.hasOrderingWindowControls(), "Ordering window controls should be visible");
        Assert.assertTrue(operationsPage.hasCutoffControls(), "Cutoff controls should be visible");
        Assert.assertTrue(operationsPage.hasStatusSummary(), "Operations status summary should be visible");
    }

    @Test(description = "TC-072 to TC-074: Verify admin vendor management create and list controls",
            groups = {"smoke", "regression", "rbac", "uat"})
    public void shouldDisplayAdminVendorManagement() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminVendorsPage vendorsPage = new AdminVendorsPage(getDriver());
        vendorsPage.open();

        Assert.assertTrue(vendorsPage.isDisplayed(), "Vendor management page should be visible");
        Assert.assertTrue(vendorsPage.hasVendorState(), "Vendor management should show list or empty state");
        vendorsPage.openCreateVendorForm();
        Assert.assertTrue(vendorsPage.isVendorFormDisplayed(), "Create vendor form should open");
    }

    @Test(description = "TC-087 to TC-089: Verify admin menu management controls",
            groups = {"regression", "sanity"})
    public void shouldDisplayAdminMenuManagement() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();

        Assert.assertTrue(managePage.isDisplayed(), "Admin menu management should be visible");
        Assert.assertTrue(managePage.hasMenuManagementState(), "Admin menu management should show list or empty state");
    }

    @Test(description = "TC-096 to TC-099: Verify admin vendor create validation path",
            groups = {"regression", "sanity", "usability"})
    public void shouldShowVendorCreateFeedback() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminVendorsPage vendorsPage = new AdminVendorsPage(getDriver());
        vendorsPage.open();
        vendorsPage.createVendor(TestDataFactory.uniqueName("QA Vendor"),
                "qa.vendor." + System.currentTimeMillis() + "@cafetron.test",
                "9876543210",
                "QA Contact");

        Assert.assertTrue(vendorsPage.hasFeedback() || vendorsPage.hasVendorState(),
                "Saving vendor should show feedback or updated vendor state");
    }
}
