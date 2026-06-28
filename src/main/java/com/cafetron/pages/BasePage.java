package com.cafetron.pages;

import com.cafetron.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void type(By locator, String value) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void selectByValue(By locator, String value) {
        new Select(waitForVisible(locator)).selectByValue(value);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException exception) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        try {
            waitForPresent(locator);
            return true;
        } catch (NoSuchElementException | TimeoutException exception) {
            return false;
        }
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    protected String getOptionalText(By locator) {
        return isDisplayed(locator) ? getText(locator) : "";
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected int count(By locator) {
        return findAll(locator).size();
    }

    protected void navigateTo(String path) {
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        driver.get(ConfigReader.get("baseUrl").replaceAll("/+$", "") + normalizedPath);
    }

    public boolean waitForUrlContains(String partialUrl) {
        return wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    public boolean currentUrlContains(String partialUrl) {
        return driver.getCurrentUrl().contains(partialUrl);
    }
}
