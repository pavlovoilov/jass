package com.jass.core.handlers;

import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class WindowSizeHandler {

    private static Logger log = LoggerFactory.getLogger(WindowSizeHandler.class);

    public static void setWindowSize(int x, int y) {
        DRIVER().manage().window().setSize(new Dimension(x, y));
        log.info("Set window size to x=" + String.valueOf(x) + " and y=" + String.valueOf(y));
    }
}
