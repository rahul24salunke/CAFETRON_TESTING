package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By page = By.id("cart-page");
    private final By emptyState = By.id("cart-empty-state");
    private final By itemsList = By.id("cart-items-list");
    private final By summaryCard = By.id("cart-summary-card");
    private final By checkoutButton = By.id("cart-checkout-btn");
    private final By clearButton = By.id("cart-clear-btn");
    private final By backToMenuButton = By.id("cart-back-to-menu-btn");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/cart");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasCartState() {
        return isDisplayed(emptyState) || isDisplayed(itemsList) || isDisplayed(summaryCard);
    }

    public boolean isCheckoutAvailable() {
        return isDisplayed(checkoutButton);
    }

    public boolean isClearAvailable() {
        return isDisplayed(clearButton);
    }

    public boolean isBackToMenuAvailable() {
        return isDisplayed(backToMenuButton);
    }
}
