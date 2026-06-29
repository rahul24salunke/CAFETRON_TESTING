package com.cafetron.tests.registration;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.data.TestUser;
import com.cafetron.flows.RegistrationFlow;
import com.cafetron.pages.LoginPage;
import com.cafetron.pages.MenuPage;
import com.cafetron.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test(description = "TC-006 to TC-008: Verify role-based account registration form is available",
            groups = {"regression", "uat", "usability"})
    public void shouldDisplayRegistrationForm() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        registerPage.open();

        Assert.assertTrue(registerPage.isDisplayed(), "Registration form should be visible");
        Assert.assertTrue(registerPage.isLoginLinkDisplayed(), "Sign-in link should be available");
    }

    @Test(description = "TC-006: Verify employee registration creates login-capable account",
            groups = {"regression", "uat"})
    public void shouldRegisterEmployeeThroughUiAndLogin() {
        TestUser user = TestDataFactory.uniqueUser(Role.EMPLOYEE);
        boolean registered = new RegistrationFlow(getDriver()).register(user);
        Assert.assertTrue(registered, "Employee registration should show success or return to login");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(user.employeeId(), user.password());

        MenuPage menuPage = new MenuPage(getDriver());
        Assert.assertTrue(menuPage.waitForUrlContains("/menu"), "Created employee should log in to menu");
    }

    @Test(description = "TC-009 to TC-011: Verify registration field validation",
            groups = {"regression", "sanity", "usability"})
    public void shouldValidateBlankRegistrationForm() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        registerPage.open();
        registerPage.submitBlankForm();

        Assert.assertTrue(registerPage.isErrorDisplayed(), "Blank registration should show validation feedback");
    }

    @Test(description = "DF-018: Public registration should not allow unauthenticated Admin account creation",
            groups = {"defect", "regression", "rbac"})
    public void shouldBlockPublicAdminRegistration() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        registerPage.open();
        registerPage.register(TestDataFactory.uniqueUser(Role.ADMIN));

        Assert.assertFalse(registerPage.isSuccessDisplayed(), "Public Admin registration should not succeed");
    }
}
