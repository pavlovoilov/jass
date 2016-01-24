package com.jass.utils;

public class CustomProperties {

    private static String PORT;
    private static String HOST;
    private static String SCHEMA;
    private static String XVFB_PORT;
    private static String BROWSER;
    private static String REMOTE;
    private static String DRIVER;
    private static String iOS_DEVICE_NAME;
    private static String iOS_PLATFORM_VERSION;
    private static int DRIVER_TIMEOUT_CREATION;
    private static int DRIVER_CREATION_ATTEMPTS;
    private static int WINDOW_X_SIZE;
    private static int WINDOW_Y_SIZE;
    private static String CHROME_DRIVER_LOCATION;
    private static String IE_DRIVER_LOCATION;
    private static String FIREFOX_BINAY_LOCATION;

    static {
        SCHEMA = System.getProperty("schema", "http").trim();
        HOST = System.getProperty("host", "localhost").trim();
        PORT = System.getProperty("port", "8080").trim();
        XVFB_PORT = System.getProperty("xvfb_port", "98");
        BROWSER = System.getProperty("browser", "chrome");
        REMOTE = System.getProperty("remote", null);
        DRIVER = System.getProperty("driver", "chrome");
        DRIVER_TIMEOUT_CREATION = Integer.valueOf(System.getProperty("driver_timeout_creation", "30"));
        DRIVER_CREATION_ATTEMPTS = Integer.valueOf(System.getProperty("driver_creation_attempts", "5"));
        WINDOW_X_SIZE = Integer.valueOf(System.getProperty("window_x_size", "1600"));
        WINDOW_Y_SIZE = Integer.valueOf(System.getProperty("window_y_size", "900"));
        iOS_DEVICE_NAME = System.getProperty("ios_device_name", "iPad Air");
        iOS_PLATFORM_VERSION = System.getProperty("ios_platform_version", "9.1");
        CHROME_DRIVER_LOCATION = System.getProperty("chrome_driver_location", "/usr/local/bin/chromedriver");
        IE_DRIVER_LOCATION = System.getProperty("ie_driver_location", "c:/development/drivers/IEDriver.exe");
        FIREFOX_BINAY_LOCATION = System.getProperty("firefox_binary_location", "usr/local/bin/firefox");
    }

    public static String getHost() {return HOST; }

    public static String getPort() {
        return PORT;
    }

    public static String getSchema() {
        return SCHEMA;
    }

    public static String getDefaultServer() {
        return String.format("%s://%s:%s/", SCHEMA, HOST, PORT);
    }

    public static String getXvfbPort() { return XVFB_PORT; }

    public static String getBrowser() { return BROWSER; }

    public static String getRemote() { return REMOTE; }

    public static String getDriver() { return DRIVER; }

    public static int getDriverTimeoutCreation() {
        return DRIVER_TIMEOUT_CREATION;
    }

    public static int getDriverCreationAttempts() {
        return DRIVER_CREATION_ATTEMPTS;
    }

    public static int getWindowXSize() {
        return WINDOW_X_SIZE;
    }

    public static int getWindowYSize() {
        return WINDOW_Y_SIZE;
    }

    public static String getiOSDeviceName() {
        return iOS_DEVICE_NAME;
    }

    public static String getiOSPlatformName() {
        return iOS_PLATFORM_VERSION;
    }

    public static String getChromeDriverLocation() {
        return CHROME_DRIVER_LOCATION;
    }

    public static String getIeDriverLocation() {
        return IE_DRIVER_LOCATION;
    }

    public static String getFirefoxBinayLocation() {
        return FIREFOX_BINAY_LOCATION;
    }
}