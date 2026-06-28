package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QrScannerPage extends BasePage {
    private final By page = By.id("qr-scanner-page");
    private final By startButton = By.id("qr-scanner-start-btn");
    private final By cameraFrame = By.id("qr-scanner-camera-frame");
    private final By video = By.id("qr-scanner-video");
    private final By error = By.id("qr-scanner-error");
    private final By result = By.id("qr-scanner-result");

    public QrScannerPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/vendor/scanner");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public void startScanner() {
        click(startButton);
    }

    public boolean hasScannerFeedback() {
        return isDisplayed(cameraFrame) || isDisplayed(video) || isDisplayed(error) || isDisplayed(result);
    }
}
