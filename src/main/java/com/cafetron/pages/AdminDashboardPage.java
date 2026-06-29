package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminDashboardPage extends BasePage {
    private final By page = By.id("admin-dashboard-page");
    private final By totalOrders = By.id("admin-dashboard-total-orders-value");
    private final By revenue = By.id("admin-dashboard-revenue-value");
    private final By dailyCsvButton = By.id("admin-dashboard-daily-csv-btn");
    private final By rangeFromInput = By.id("admin-dashboard-range-from-input");
    private final By rangeToInput = By.id("admin-dashboard-range-to-input");
    private final By manageVendorsLink = By.id("admin-dashboard-manage-vendors-link");

    public void downloadDailyCsv() {
        WebElement csvButton = waitForClickable(dailyCsvButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", csvButton);
        js.executeScript("arguments[0].click();", csvButton);
    }

    public void setDateRange(String fromDate, String toDate) {
        type(rangeFromInput, fromDate);
        type(rangeToInput, toDate);
    }

    public String totalOrdersText() {
        return getOptionalText(totalOrders);
    }

    public String revenueText() {
        return getOptionalText(revenue);
    }
    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/admin");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasKpis() {
        return isDisplayed(totalOrders) && isDisplayed(revenue);
    }

    public boolean hasCsvControls() {
        return isDisplayed(dailyCsvButton);
    }

    public boolean hasDateFilters() {
        return isDisplayed(rangeFromInput) && isDisplayed(rangeToInput);
    }

    public boolean hasManageVendorNavigation() {
        return isDisplayed(manageVendorsLink);
    }
}
