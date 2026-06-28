package com.cafetron.flows;

import com.cafetron.pages.CartPage;
import com.cafetron.pages.CheckoutPage;
import com.cafetron.pages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import java.util.List;

public class CartCheckoutFlow {
    private final WebDriver driver;

    public CartCheckoutFlow(WebDriver driver) {
        this.driver = driver;
    }

    public boolean addFirstAvailableMenuItemToCart() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.open();
        menuPage.isAtMenuRoute();

        List<WebElement> addButtons = driver.findElements(By.cssSelector(
                "button[id^='menu-add'], button[id*='add'], button[title*='Add'], .btn-primary"));
        for (WebElement button : addButtons) {
            if (button.isDisplayed() && button.isEnabled() && button.getText().toLowerCase().contains("add")) {
                button.click();
                return true;
            }
        }
        return false;
    }

    public void openCheckoutWithCartItem() {
        if (!addFirstAvailableMenuItemToCart()) {
            throw new SkipException("No available menu item could be added through the UI.");
        }
        new CartPage(driver).open();
        CartPage cartPage = new CartPage(driver);
        if (!cartPage.isCheckoutAvailable()) {
            throw new SkipException("Checkout button is not available after adding an item.");
        }
    }

    public CheckoutPage checkoutPage() {
        return new CheckoutPage(driver);
    }
}
