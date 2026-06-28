package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By overviewViewButton = By.id("checkout-overview-view-btn");
    private final By cardsViewButton = By.id("checkout-cards-view-btn");
    private final By overviewLocationInput = By.id("checkout-overview-location-input");
    private final By cardLocationInput = By.id("checkout-card-location-input");
    private final By overviewPlaceOrderButton = By.id("checkout-overview-place-order-btn");
    private final By cardPlaceOrderButton = By.id("checkout-card-place-order-btn");
    private final By overviewTotal = By.id("checkout-overview-total");
    private final By cardTotal = By.id("checkout-card-total");
    private final By errorAlert = By.id("checkout-error-alert");
    private final By toast = By.id("checkout-toast");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/checkout");
    }

    public boolean isDisplayed() {
        return isDisplayed(overviewViewButton) || isDisplayed(cardsViewButton)
                || isDisplayed(overviewPlaceOrderButton) || isDisplayed(cardPlaceOrderButton);
    }

    public boolean hasTotals() {
        return isDisplayed(overviewTotal) || isDisplayed(cardTotal);
    }

    public void enterPickupLocation(String location) {
        if (isDisplayed(overviewLocationInput)) {
            type(overviewLocationInput, location);
        } else {
            type(cardLocationInput, location);
        }
    }

    public boolean isPlaceOrderAvailable() {
        return isDisplayed(overviewPlaceOrderButton) || isDisplayed(cardPlaceOrderButton);
    }

    public boolean hasFeedback() {
        return isDisplayed(errorAlert) || isDisplayed(toast);
    }
}
