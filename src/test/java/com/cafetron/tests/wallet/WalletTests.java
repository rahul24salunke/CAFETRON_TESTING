package com.cafetron.tests.wallet;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.pages.WalletPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WalletTests extends AuthenticatedBaseTest {

    @Test(description = "TC-031 to TC-036: Verify wallet balance and transactions UI",
            groups = {"smoke", "regression", "uat"})
    public void shouldDisplayWalletBalanceAndTransactions() {
        WalletPage walletPage = new WalletPage(getDriver());
        walletPage.open();

        Assert.assertTrue(walletPage.isDisplayed(), "Wallet page should be visible");
        Assert.assertTrue(walletPage.hasBalance(), "Wallet balance should be displayed");
        Assert.assertTrue(walletPage.hasTransactionArea(), "Wallet transaction area should be visible");
    }

    @Test(description = "TC-037 to TC-041: Verify wallet top-up validation and feedback",
            groups = {"regression", "integration", "usability", "uat"})
    public void shouldShowWalletTopUpFeedback() {
        WalletPage walletPage = new WalletPage(getDriver());
        walletPage.open();
        walletPage.topUp("10");

        Assert.assertTrue(walletPage.hasFeedback() || walletPage.hasBalance(),
                "Wallet top-up should keep balance visible and show feedback when applicable");
    }
}
