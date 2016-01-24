package com.jass.core.webdrivers;

import com.jass.utils.CustomProperties;

public class Driver {

    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "internet explorer";
    public static final String SAFARI = "safari";
    public static final String IPAD = "ipad";

    public static boolean isIE() {
        return CustomProperties.getDriver().contains("ie");
    }

    public static boolean isSafari() {
        return CustomProperties.getDriver().contains("safari");
    }

    public static boolean isIpad() {
        return CustomProperties.getDriver().contains("ipad");
    }

    public static boolean isFirefox() {
        return CustomProperties.getDriver().contains("firefox");
    }
}