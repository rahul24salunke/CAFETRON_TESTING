package com.cafetron.base;

import com.cafetron.data.Role;
import com.cafetron.flows.AuthFlow;
import org.testng.annotations.BeforeMethod;

public abstract class AuthenticatedBaseTest extends BaseTest {

    protected Role requiredRole() {
        return Role.EMPLOYEE;
    }

    @BeforeMethod(alwaysRun = true)
    public void authenticate() {
        new AuthFlow(getDriver()).loginAs(requiredRole());
    }
}
