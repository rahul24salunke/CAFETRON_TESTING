package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VendorMenuManagePage extends BasePage {
    private final By page = By.id("menu-manage-page");
    private final By addItemButton = By.id("menu-manage-add-item-btn");
    private final By itemsGrid = By.id("menu-manage-items-grid");
    private final By emptyState = By.id("menu-manage-empty-state");
    private final By form = By.id("menu-item-form");
    private final By nameInput = By.id("menu-item-name-input");
    private final By priceInput = By.id("menu-item-price-input");
    private final By stockInput = By.id("menu-item-stock-input");
    private final By foodTypeInput = By.id("menu-item-food-type-input");
    private final By submitButton = By.id("menu-item-form-submit-btn");
    private final By toast = By.id("menu-manage-toast");

    public VendorMenuManagePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/menu/manage");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasMenuManagementState() {
        return isDisplayed(itemsGrid) || isDisplayed(emptyState);
    }

    public void openCreateItemForm() {
        click(addItemButton);
    }

    public boolean isItemFormDisplayed() {
        return isDisplayed(form);
    }

    public void createItem(String name, String price, String stock, String foodType) {
        openCreateItemForm();
        type(nameInput, name);
        type(priceInput, price);
        type(stockInput, stock);
        type(foodTypeInput, foodType);
        click(submitButton);
    }

    public boolean hasSaveFeedback() {
        return isDisplayed(toast);
    }

    public String saveFeedbackText() {
        return getOptionalText(toast);
    }
}
