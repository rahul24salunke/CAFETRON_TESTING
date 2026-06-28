package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuPage extends BasePage {
    private final By menuBrowsePage = By.id("menu-browse-page");
    private final By profileLink = By.id("menu-profile-link");
    private final By logoutButton = By.id("menu-logout-btn");
    private final By searchInput = By.id("menu-search-input");
    private final By itemsGrid = By.id("menu-items-grid");
    private final By emptyState = By.id("menu-empty-state");
    private final By cartPreviewButton = By.id("menu-cart-preview-btn");
    private final By floatingCartButton = By.id("menu-floating-cart-btn");
    private final By cartDrawer = By.id("menu-cart-drawer");
    private final By checkoutButton = By.id("menu-cart-checkout-btn");
    private final By manageMenuLink = By.id("menu-manage-link");

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/menu");
    }

    public boolean isAtMenuRoute() {
        return waitForUrlContains("/menu");
    }

    public boolean isMenuPageDisplayed() {
        return isDisplayed(menuBrowsePage)
                && isDisplayed(profileLink)
                && isDisplayed(logoutButton);
    }

    public boolean hasMenuResultsArea() {
        return isDisplayed(itemsGrid) || isDisplayed(emptyState);
    }

    public void search(String term) {
        type(searchInput, term);
    }

    public boolean isSearchAvailable() {
        return isDisplayed(searchInput);
    }

    public int visibleMenuCardCount() {
        return count(By.cssSelector("[id^='menu-item-card-'], .menu-card, article"));
    }

    public boolean isCartControlVisible() {
        return isDisplayed(cartPreviewButton) || isDisplayed(floatingCartButton);
    }

    public void openCartPreview() {
        if (isDisplayed(cartPreviewButton)) {
            click(cartPreviewButton);
        } else {
            click(floatingCartButton);
        }
    }

    public boolean isCartDrawerDisplayed() {
        return isDisplayed(cartDrawer);
    }

    public boolean isCheckoutButtonDisplayed() {
        return isDisplayed(checkoutButton);
    }

    public boolean isManageMenuLinkDisplayed() {
        return isDisplayed(manageMenuLink);
    }

    public boolean hasEmployeeOrderingControls() {
        return isCartControlVisible() || isDisplayed(By.cssSelector("button[id*='add'], button[title*='Add']"));
    }

    public void logout() {
        click(logoutButton);
    }
}
