package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WalletPage extends BasePage {
    private final By page = By.id("wallet-page");
    private final By balanceCard = By.id("wallet-balance-card");
    private final By balanceValue = By.id("wallet-balance-value");
    private final By topUpInput = By.id("wallet-topup-amount-input");
    private final By topUpButton = By.id("wallet-topup-submit-btn");
    private final By transactionList = By.id("wallet-transaction-list");
    private final By emptyTransactions = By.id("wallet-transactions-empty-state");
    private final By successMessage = By.id("wallet-success-message");
    private final By errorMessage = By.id("wallet-error-message");

    public WalletPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/wallet");
    }

    public boolean isDisplayed() {
        return isDisplayed(page) && isDisplayed(balanceCard);
    }

    public boolean hasBalance() {
        return isDisplayed(balanceValue);
    }

    public void topUp(String amount) {
        type(topUpInput, amount);
        click(topUpButton);
    }

    public boolean hasTransactionArea() {
        return isDisplayed(transactionList) || isDisplayed(emptyTransactions);
    }

    public boolean hasFeedback() {
        return isDisplayed(successMessage) || isDisplayed(errorMessage);
    }
}
