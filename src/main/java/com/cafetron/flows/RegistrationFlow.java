package com.cafetron.flows;

import com.cafetron.data.TestUser;
import com.cafetron.pages.RegisterPage;
import org.openqa.selenium.WebDriver;

public class RegistrationFlow {
    private final WebDriver driver;

    public RegistrationFlow(WebDriver driver) {
        this.driver = driver;
    }

    public boolean register(TestUser user) {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(user);
        return registerPage.isSuccessDisplayed() || registerPage.currentUrlContains("/login");
    }
}
