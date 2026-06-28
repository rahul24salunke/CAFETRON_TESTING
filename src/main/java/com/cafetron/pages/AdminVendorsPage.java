package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminVendorsPage extends BasePage {
    private final By page = By.id("vendor-manage-page");
    private final By addVendorButton = By.id("vendor-manage-add-vendor-btn");
    private final By vendorsGrid = By.id("vendor-manage-vendors-grid");
    private final By emptyState = By.id("vendor-manage-empty-state");
    private final By form = By.id("vendor-form");
    private final By vendorNameInput = By.id("vendor-name-input");
    private final By vendorEmailInput = By.id("vendor-email-input");
    private final By vendorPhoneInput = By.id("vendor-phone-input");
    private final By vendorContactPersonInput = By.id("vendor-contact-person-input");
    private final By submitButton = By.id("vendor-form-submit-btn");
    private final By toast = By.id("vendor-manage-toast");

    public AdminVendorsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/admin/vendors");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasVendorState() {
        return isDisplayed(vendorsGrid) || isDisplayed(emptyState);
    }

    public void openCreateVendorForm() {
        click(addVendorButton);
    }

    public boolean isVendorFormDisplayed() {
        return isDisplayed(form);
    }

    public void createVendor(String name, String email, String phone, String contactPerson) {
        openCreateVendorForm();
        type(vendorNameInput, name);
        type(vendorEmailInput, email);
        type(vendorPhoneInput, phone);
        type(vendorContactPersonInput, contactPerson);
        click(submitButton);
    }

    public boolean hasFeedback() {
        return isDisplayed(toast);
    }

    public String vendorFeedbackText() {
        return getOptionalText(toast);
    }
}
