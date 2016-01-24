package com.jass.core.webdrivers;

import com.google.common.collect.ImmutableMap;
import com.jass.utils.CustomProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ChromeDriver extends DriverCreator {

    private static final String DRIVER_EXECUTABLE_PATH = CustomProperties.getChromeDriverLocation();
    private ChromeDriverService service;

    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        // Read variables
        String xvfbPort = CustomProperties.getXvfbPort();
//        log.info("Xvfb port is: " + xvfbPort);

        // start service first
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_EXECUTABLE_PATH))
                .usingAnyFreePort()
                .withEnvironment(ImmutableMap.of("DISPLAY", ":" + xvfbPort))
                .build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Then start driver, with URL mapped to above-started service URL
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);

        dc.setCapability(ChromeOptions.CAPABILITY, options);
        dc.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        return startDriver(() -> new RemoteWebDriver(service.getUrl(), dc));
    }

    public ChromeDriverService getService() {
        return service;
    }
}