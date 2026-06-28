package com.cafetron.pages;

import com.cafetron.data.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    private final By page = By.id("register-page");
    private final By nameInput = By.id("register-name-input");
    private final By emailInput = By.id("register-email-input");
    private final By passwordInput = By.id("register-password-input");
    private final By employeeIdInput = By.id("register-employee-id-input");
    private final By departmentInput = By.id("register-department-input");
    private final By roleSelect = By.id("register-role-select");
    private final By submitButton = By.id("register-submit-btn");
    private final By successMessage = By.id("register-success-message");
    private final By errorMessage = By.id("register-error-message");
    private final By loginLink = By.id("register-login-link");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/register");
    }

    public boolean isDisplayed() {
        return isDisplayed(page) && isDisplayed(nameInput) && isDisplayed(roleSelect);
    }

    public void register(TestUser user) {
        type(nameInput, user.name());
        type(emailInput, user.email());
        type(passwordInput, user.password());
        type(employeeIdInput, user.employeeId());
        type(departmentInput, user.department());
        selectByValue(roleSelect, user.role().name());
        click(submitButton);
    }

    public void submitBlankForm() {
        click(submitButton);
    }

    public boolean isSuccessDisplayed() {
        return isDisplayed(successMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String feedbackText() {
        String error = getOptionalText(errorMessage);
        return error.isBlank() ? getOptionalText(successMessage) : error;
    }

    public boolean isLoginLinkDisplayed() {
        return isDisplayed(loginLink);
    }
}
