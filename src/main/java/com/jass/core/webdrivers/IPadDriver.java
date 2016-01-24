package com.jass.core.webdrivers;

import com.jass.utils.CustomProperties;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IPadDriver extends DriverCreator {

    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        dc.setCapability("deviceName", CustomProperties.getiOSDeviceName());
        dc.setCapability("platformName", "iOS");
        dc.setCapability("platformVersion", CustomProperties.getiOSPlatformName());
        dc.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        dc.setCapability("orientation", "LANDSCAPE");

        return startDriver(() -> new IOSDriver(prepareRemoteUrl(), dc));
    }
}