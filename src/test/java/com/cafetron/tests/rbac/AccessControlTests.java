package com.cafetron.tests.rbac;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.flows.AuthFlow;
import com.cafetron.pages.LoginPage;
import com.cafetron.pages.MenuPage;
import com.cafetron.pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccessControlTests extends BaseTest {

    @Test(description = "TC-067 to TC-074: Verify unauthenticated protected routes redirect to login",
            groups = {"smoke", "regression", "rbac"})
    public void shouldRedirectUnauthenticatedUsersToLogin() {
        getDriver().get(com.cafetron.config.ConfigReader.get("baseUrl").replaceAll("/+$", "") + "/wallet");
        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertTrue(loginPage.waitForUrlContains("/login"), "Unauthenticated user should be sent to login");
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login UI should be shown");
    }

    @Test(description = "TC-077 to TC-081: Verify employee profile and logout flow",
            groups = {"sanity", "regression", "rbac"})
    public void shouldAllowEmployeeLogoutFromProfile() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.open();

        Assert.assertTrue(profilePage.isDisplayed(), "Profile page should be visible");
        Assert.assertTrue(profilePage.hasAuthenticatedNavigation(), "Profile should show authenticated navigation");
        profilePage.logout();

        Assert.assertTrue(new LoginPage(getDriver()).waitForUrlContains("/login"),
                "Logout should return user to login");
    }

    @Test(description = "TC-090: Verify authenticated employee can use app shell navigation",
            groups = {"regression", "rbac", "usability"})
    public void shouldDisplayEmployeeNavigationShell() {
        new AuthFlow(getDriver()).loginAs(Role.EMPLOYEE);
        MenuPage menuPage = new MenuPage(getDriver());

        Assert.assertTrue(menuPage.isMenuPageDisplayed(), "Menu page should show authenticated navigation");
        Assert.assertTrue(menuPage.isCartControlVisible(), "Employee should see cart navigation");
    }
}
