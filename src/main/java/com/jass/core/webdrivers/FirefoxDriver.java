package com.jass.core.webdrivers;

import com.jass.utils.CustomProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirefoxDriver extends DriverCreator {

    private Logger log = LoggerFactory.getLogger(FirefoxDriver.class);

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        // Read variables
        String xvfbPort = CustomProperties.getXvfbPort();
        log.info("Xvfb port is: " + xvfbPort);

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.setEnvironmentProperty("DISPLAY", ":" + xvfbPort);

        RemoteWebDriver driver = startDriver(() -> new org.openqa.selenium.firefox.FirefoxDriver(firefoxBinary, null));
        driver.manage().window().maximize();
        return driver;
    }
}