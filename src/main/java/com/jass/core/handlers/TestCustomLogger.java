package com.jass.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;

public class TestCustomLogger implements ITestListener {

    private Logger log = LoggerFactory.getLogger(TestCustomLogger.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test STARTED: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test PASSED: " + result.getName());
        AttachmentsHandler.makeScreenshot();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        AttachmentsHandler.makeScreenshot();
        log.info("Test FAILED: " + result.getName());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
        AttachmentsHandler.getPageSource();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test SKIPPED: " + result.getName());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
        AttachmentsHandler.makeScreenshot();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        AttachmentsHandler.makeScreenshot();
        AttachmentsHandler.getPageSource();
    }

    @Override
    public void onStart(ITestContext testContext) {
        log.info("Class STARTED: " + ((TestRunner) testContext).getTestClasses());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        log.info("Class FINISHED: " + ((TestRunner) testContext).getTestClasses());
    }
}
