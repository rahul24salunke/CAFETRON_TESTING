package com.cafetron.flows;

import com.cafetron.config.ConfigReader;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.data.TestUser;
import com.cafetron.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

import java.util.HashSet;
import java.util.Set;

public class AuthFlow {
    private static final Set<String> REGISTERED_USERS = new HashSet<>();
    private final WebDriver driver;

    public AuthFlow(WebDriver driver) {
        this.driver = driver;
    }

    public TestUser loginAs(Role role) {
        TestUser user = TestDataFactory.configuredOrGeneratedUser(role);
        if (requiresUiRegistration(role, user)) {
            registerThroughUi(user);
        }

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        if (loginPage.currentUrlContains(user.role().landingPath())) {
            return user;
        }
        loginPage.login(user.employeeId(), user.password());
        loginPage.waitForUrlContains(user.role().landingPath());
        return user;
    }

    public void logoutEmployeeFromMenuIfPossible() {
        try {
            new com.cafetron.pages.MenuPage(driver).logout();
        } catch (RuntimeException ignored) {
            // Best-effort cleanup for tests that intentionally navigate across pages.
        }
    }

    private boolean requiresUiRegistration(Role role, TestUser user) {
        if (role == Role.EMPLOYEE) {
            return false;
        }
        String idKey = role.name().toLowerCase() + "EmployeeId";
        String passwordKey = role.name().toLowerCase() + "Password";
        boolean configured = !ConfigReader.getOptional(idKey).isBlank()
                && !ConfigReader.getOptional(passwordKey).isBlank();
        return !configured && !REGISTERED_USERS.contains(user.employeeId());
    }

    private void registerThroughUi(TestUser user) {
        boolean registered = new RegistrationFlow(driver).register(user);
        if (!registered) {
            throw new SkipException("Could not create " + user.role() + " test user through UI. "
                    + "Provide " + user.role().name().toLowerCase() + "EmployeeId and "
                    + user.role().name().toLowerCase() + "Password in config to run this test.");
        }
        REGISTERED_USERS.add(user.employeeId());
    }
}
