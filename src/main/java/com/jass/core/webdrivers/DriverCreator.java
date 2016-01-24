package com.jass.core.webdrivers;

import com.jass.utils.CustomProperties;
import com.jass.utils.FutureTaskUtils;
import com.jass.utils.URLUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.*;

public abstract class DriverCreator {

    Logger log = LoggerFactory.getLogger(DriverCreator.class);

    public RemoteWebDriver startDriver(Callable<RemoteWebDriver> task) {
        RemoteWebDriver driver = invokeDriverCreationTask(task);

        int driverCreationAttempts = CustomProperties.getDriverCreationAttempts();
        int counter = 0;
        while (driver == null && counter != driverCreationAttempts) {
            driver = invokeDriverCreationTask(task);
            counter++;

            if (counter == driverCreationAttempts && driver == null)
                throw new WebDriverException("Failed to create new instance of WebDriver after " + driverCreationAttempts + " attempts!");
        }
        return driver;
    }

    private RemoteWebDriver invokeDriverCreationTask(Callable<RemoteWebDriver> task) {
        int timeout = CustomProperties.getDriverTimeoutCreation(); //in seconds

        RemoteWebDriver driver = FutureTaskUtils.invokeTask(task, timeout);

        if (driver == null) {
            log.warn("Failed to created WebDriver instance, will try one more time.");
        }
        return driver;
    }

    protected URL prepareRemoteUrl() {
        URL url = null;
        if (CustomProperties.getRemote() != null) {
            url = URLUtils.getURL(CustomProperties.getRemote());
        }
        return url;
    }

    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        throw  new UnsupportedOperationException("WebDriver should be implemented for specific browser!");
    }
}