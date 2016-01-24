package com.jass.core.webdrivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;

public class SafariCustomDriver extends DriverCreator {

    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        URL remoteUrl = prepareRemoteUrl();
        RemoteWebDriver driver = startDriver(() -> remoteUrl != null
                ? new RemoteWebDriver(remoteUrl, dc)
                : new SafariDriver(new SafariOptions()));
        driver.manage().window().maximize();
        return driver;
    }
}