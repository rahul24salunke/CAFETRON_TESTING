package com.cafetron.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cafetron.base.BaseTest;
import com.cafetron.reports.ExtentReportManager;
import com.cafetron.utilities.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = ExtentReportManager.getExtentReports()
                .createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestClass().getName());
        TEST.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = TEST.get();
        extentTest.fail(result.getThrowable());

        try {
            String screenshotPath = getExistingScreenshotPath(result);
            WebDriver driver = getDriverFromResult(result);
            if (screenshotPath == null && driver != null) {
                screenshotPath = captureAndStoreScreenshot(result, driver);
            }

            if (screenshotPath != null) {
                extentTest.fail("Failure screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                extentTest.warning("Screenshot was not captured because WebDriver was not available.");
            }
        } catch (RuntimeException exception) {
            extentTest.warning("Screenshot capture failed: " + exception.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getExtentReports().flush();
        TEST.remove();
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            return ((BaseTest) testInstance).getDriver();
        }
        return null;
    }

    private String getExistingScreenshotPath(ITestResult result) {
        Object existingPath = result.getAttribute(ScreenshotUtils.SCREENSHOT_PATH_ATTRIBUTE);
        if (existingPath instanceof String && !((String) existingPath).isBlank()) {
            return (String) existingPath;
        }
        return null;
    }

    private String captureAndStoreScreenshot(ITestResult result, WebDriver driver) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
        result.setAttribute(ScreenshotUtils.SCREENSHOT_PATH_ATTRIBUTE, screenshotPath);
        return screenshotPath;
    }
}
