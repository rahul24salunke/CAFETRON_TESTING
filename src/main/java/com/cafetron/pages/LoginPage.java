package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By loginPage = By.id("login-page");
    private final By employeeIdInput = By.id("login-employee-id-input");
    private final By passwordInput = By.id("login-password-input");
    private final By submitButton = By.id("login-submit-btn");
    private final By errorMessage = By.id("login-error-message");
    private final By registerLink = By.id("login-register-link");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/login");
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginPage)
                && isDisplayed(employeeIdInput)
                && isDisplayed(passwordInput)
                && isDisplayed(submitButton);
    }

    public void login(String employeeId, String password) {
        enterEmployeeId(employeeId);
        enterPassword(password);
        clickSignIn();
    }

    public void enterEmployeeId(String employeeId) {
        type(employeeIdInput, employeeId);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickSignIn() {
        click(submitButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isEmployeeIdRequired() {
        return Boolean.parseBoolean(waitForVisible(employeeIdInput).getAttribute("required"));
    }

    public boolean isPasswordRequired() {
        return Boolean.parseBoolean(waitForVisible(passwordInput).getAttribute("required"));
    }

    public boolean isRegisterLinkDisplayed() {
        return isDisplayed(registerLink);
    }
}
