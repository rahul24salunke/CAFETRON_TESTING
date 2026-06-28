package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrdersPage extends BasePage {
    private final By page = By.id("orders-page");
    private final By list = By.id("orders-list");
    private final By emptyState = By.id("orders-empty-state");
    private final By errorAlert = By.id("orders-error-alert");
    private final By startOrderingLink = By.id("orders-start-ordering-link");

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/orders");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasOrderState() {
        return isDisplayed(list) || isDisplayed(emptyState) || isDisplayed(errorAlert);
    }

    public boolean isStartOrderingAvailableWhenEmpty() {
        return !isDisplayed(emptyState) || isDisplayed(startOrderingLink);
    }
}
