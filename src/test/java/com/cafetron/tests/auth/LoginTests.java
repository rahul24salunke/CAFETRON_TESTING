package com.cafetron.tests.auth;

import com.cafetron.base.BaseTest;
import com.cafetron.config.ConfigReader;
import com.cafetron.data.Role;
import com.cafetron.flows.AuthFlow;
import com.cafetron.pages.AdminDashboardPage;
import com.cafetron.pages.LoginPage;
import com.cafetron.pages.MenuPage;
import com.cafetron.pages.VendorOrdersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "TC-001: Verify employee login with valid credentials",
            groups = {"smoke", "regression", "rbac", "uat"})
    public void shouldLoginWithValidEmployeeCredentials() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        MenuPage menuPage = new MenuPage(getDriver());

        Assert.assertTrue(menuPage.currentUrlContains("/menu"), "Employee should land on /menu");
        Assert.assertTrue(menuPage.isMenuPageDisplayed(), "Employee menu UI should be visible");
    }

    @Test(description = "TC-002: Verify vendor login with valid credentials",
            groups = {"smoke", "regression", "rbac", "uat"})
    public void shouldLoginWithVendorCredentials() {
        new AuthFlow(getDriver()).loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(getDriver());

        Assert.assertTrue(vendorOrdersPage.currentUrlContains("/vendor/orders"), "Vendor should land on vendor orders");
        Assert.assertTrue(vendorOrdersPage.isDisplayed(), "Vendor orders UI should be visible");
    }

    @Test(description = "TC-003: Verify admin login with valid credentials",
            groups = {"smoke", "regression", "rbac", "uat"})
    public void shouldLoginWithAdminCredentials() {
        new AuthFlow(getDriver()).loginAs(Role.ADMIN);
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(getDriver());

        Assert.assertTrue(adminDashboardPage.currentUrlContains("/admin"), "Admin should land on admin dashboard");
        Assert.assertTrue(adminDashboardPage.isDisplayed(), "Admin dashboard UI should be visible");
    }

    @Test(description = "TC-004: Verify invalid login credentials are rejected",
            groups = {"regression", "sanity", "usability"})
    public void shouldRejectInvalidCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(ConfigReader.get("invalidEmployeeId"), ConfigReader.get("invalidPassword"));

        Assert.assertTrue(loginPage.currentUrlContains("/login"), "Invalid login should stay on login page");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Invalid login should show visible UI feedback");
    }

    @Test(description = "TC-005: Verify blank login is rejected without creating a session",
            groups = {"regression", "sanity", "usability"})
    public void shouldValidateRequiredLoginFields() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be visible");
        Assert.assertTrue(loginPage.isEmployeeIdRequired(), "Employee ID should be required");
        Assert.assertTrue(loginPage.isPasswordRequired(), "Password should be required");
    }
}
