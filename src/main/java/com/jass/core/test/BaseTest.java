package com.jass.core.test;

import com.jass.core.handlers.ConsoleErrorsHandler;
import com.jass.core.handlers.TestCustomLogger;
import com.jass.core.webdrivers.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({TestCustomLogger.class})
public abstract class BaseTest {

    private Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected String testMethodName = "";
    protected ConsoleErrorsHandler consoleErrors = new ConsoleErrorsHandler();

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.setWebDriver();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        consoleErrors.collectErrors(result);
    }

    @AfterClass(alwaysRun = true)
    public void browserConsoleErrors() {
        consoleErrors.checkForErrorsAndAssert();
        WebDriverManager.closeWebDriver();
    }

}