package com.jass.core.handlers;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;
import static com.jass.utils.SleepUtils.sleep;

public class AlertsHandler {

    private static Logger log = LoggerFactory.getLogger(AlertsHandler.class);

    /**
     * Method closing tries to close all alerts. Many retries due to different types of alerts, can be unhandled or
     * unexpected.
     */
    public static void closeAlerts() {
        log.info("Closing alerts if any...");
        int counter = 3;

        while (counter != 0) {
            try {
                DRIVER().switchTo().alert().accept();
                log.info("Successfully closed alert!");
                return;
            } catch (NoAlertPresentException e) {
                return;
            } catch (UnhandledAlertException e) {
                log.warn("Caught Unhandled Alert!");
                sleep(200);
                counter--;
            } catch (WebDriverException e) {
                sleep(200);
                counter--;
            }
        }
    }
}
