package com.jass.core.handlers;

import com.jass.core.webdrivers.Driver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class ConsoleErrorsHandler {

    private Logger log = LoggerFactory.getLogger(ConsoleErrorsHandler.class);
    private List<String> knownErrors = new ArrayList<>();
    private String registeredErrors = "";

    public ConsoleErrorsHandler() {
        addKnownErrors();
    }

    public void collectErrors(ITestResult result) {
        String test = result.getName();
        List<String> newlyLoggedErrors = getBrowserLevelSevereMessages();

        if (newlyLoggedErrors.isEmpty()) return;

        registeredErrorsLoop:
        for (String error : newlyLoggedErrors) {
            if (registeredErrors.contains(error)) continue registeredErrorsLoop;

            for (String knownError: knownErrors) {
                if (error.contains(knownError)) continue registeredErrorsLoop;
            }

            log.error(error);
            registeredErrors += "\n<=== TEST: " + test + " ===>\nError text: " + error + "\n";
        }
    }

    public void checkForErrorsAndAssert() {
        if (!registeredErrors.isEmpty()) {
            throw new AssertionError("There were JavaScript errors:\n" + registeredErrors);
        }
    }

    public ConsoleErrorsHandler addErrorToKnownList(String... errors) {
        for (String error: errors) {
            knownErrors.add(error);
        }
        return this;
    }

    public List<String> getKnownErrors() {
        return knownErrors;
    }

    private void addKnownErrors() {
//        knownErrors.add("400 (Bad Request)");
//        knownErrors.add("403 (Forbidden)");
//        knownErrors.add("403 (Access is denied)");
//        knownErrors.add("404 (Not Found)");
//        knownErrors.add("404 (OK)");
    }

    private List<String> getBrowserLevelSevereMessages() {
        List<String> severMessages = new ArrayList<>();

        // This WebDriver feature works only for Chrome, Firefox and Safari browsers
        // Returning empty list to skip getting logs for other browsers
        if (Driver.isIE() || Driver.isIpad()) {
            return severMessages;
        }

        LogEntries entries = DRIVER().manage().logs().get(LogType.BROWSER);
        List<LogEntry> severeEntries = entries.filter(Level.SEVERE);
        severMessages.addAll(severeEntries.stream().map(LogEntry::getMessage).collect(Collectors.toList()));

        return severMessages;
    }
}