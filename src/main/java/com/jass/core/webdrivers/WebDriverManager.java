package com.jass.core.webdrivers;

import com.jass.utils.CustomProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.jass.core.handlers.CapabilitiesHandler.logBrowserProperties;
import static com.jass.core.webdrivers.Driver.*;

public class WebDriverManager {

    private static WebDriverContainer driverContainer = new WebDriverContainer();

    public static void setWebDriver() {
        if (driverContainer.hasWebDriverStarted()) return;
        WebDriver driver;
        String driverName = CustomProperties.getBrowser().toLowerCase();
        switch (driverName) {
            case CHROME:
                ChromeDriver chromeBambooDriver = new ChromeDriver();
                driver = chromeBambooDriver.createDriver(DesiredCapabilities.chrome());
                DriverServiceManager.setDriverService(chromeBambooDriver.getService());
                break;
            case FIREFOX:
                driver = new FirefoxDriver().createDriver(DesiredCapabilities.firefox());
                break;
            case INTERNET_EXPLORER:
                driver = new IEDriver().createDriver(DesiredCapabilities.internetExplorer());
                break;
            case SAFARI:
                driver = new SafariCustomDriver().createDriver(DesiredCapabilities.safari());
                break;
            case IPAD:
                driver = new IPadDriver().createDriver(new DesiredCapabilities());
                break;
            default:
                throw new IllegalArgumentException("Unknown driver: " + driverName);
        }
        driverContainer.addWebDriver(driver);
        logBrowserProperties();
    }

    public static WebDriver getWebDriver() {
        return driverContainer.getCurrentThreadWebDriver();
    }

    public static WebDriver DRIVER() {
        return getWebDriver();
    }

    public static void closeWebDriver() {
        WebDriver driver = driverContainer.getCurrentThreadWebDriver();
        driver.close();
        DriverServiceManager.stopService();
        driverContainer.killCurrentThreadWebDriver();
    }

    public static boolean hasWebDriverStarted() {
        return driverContainer.hasWebDriverStarted();
    }
}