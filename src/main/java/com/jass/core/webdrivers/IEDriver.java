package com.jass.core.webdrivers;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IEDriver extends DriverCreator {

    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        dc.setBrowserName("internet explorer");
        dc.setVersion("11");
        dc.setPlatform(Platform.WINDOWS);
        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        dc.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
        dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        dc.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // sometimes click doesn't work with enabled native events

        RemoteWebDriver driver = startDriver(() -> new RemoteWebDriver(prepareRemoteUrl(), dc));
        driver.setFileDetector(new LocalFileDetector());
        driver.manage().window().maximize();
        return driver;
    }
}