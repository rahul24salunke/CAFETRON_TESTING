package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
    private final By page = By.id("profile-page");
    private final By name = By.id("profile-name");
    private final By role = By.id("profile-role");
    private final By logoutButton = By.id("profile-logout-btn");
    private final By menuLink = By.id("profile-menu-link");
    private final By walletLink = By.id("profile-wallet-link");
    private final By ordersLink = By.id("profile-orders-link");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/profile");
    }

    public boolean isDisplayed() {
        return isDisplayed(page) && isDisplayed(name) && isDisplayed(role);
    }

    public boolean hasAuthenticatedNavigation() {
        return isDisplayed(logoutButton) && (isDisplayed(menuLink) || isDisplayed(walletLink) || isDisplayed(ordersLink));
    }

    public void logout() {
        click(logoutButton);
    }
}
