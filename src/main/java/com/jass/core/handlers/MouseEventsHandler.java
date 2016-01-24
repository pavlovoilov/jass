package com.jass.core.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class MouseEventsHandler {

    private static Logger log = LoggerFactory.getLogger(MouseEventsHandler.class);

    public static void moveMouseToElement(By locator) {
        moveMouseToElement(DRIVER().findElement(locator));
    }

    public static void moveMouseToElement(WebElement element) {
        log.info("moveMouseToElement: " + element.toString());
        new Actions(DRIVER()).moveToElement(element).build().perform();
    }
}
