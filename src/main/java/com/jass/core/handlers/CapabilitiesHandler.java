package com.jass.core.handlers;

import com.jass.core.webdrivers.Driver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class CapabilitiesHandler {

    private static Logger log = LoggerFactory.getLogger(CapabilitiesHandler.class);

    public static void logBrowserProperties() {
        if (!Driver.isSafari()) {
            DRIVER().manage().timeouts().pageLoadTimeout(120000, TimeUnit.MILLISECONDS);
        }
        BuildInfo driverInfo = new BuildInfo();
        driverInfo.getReleaseLabel();
        Capabilities cap = ((RemoteWebDriver) DRIVER()).getCapabilities();

        log.info("DRIVER INFO: " + driverInfo.getReleaseLabel());
        log.info(String.format("BROWSER: %s, %s, %s", cap.getBrowserName(), cap.getVersion(), cap.getPlatform()));
    }
}
