package com.jass.core.webdrivers;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebDriverContainer {

    private Logger log = LoggerFactory.getLogger(WebDriverContainer.class);
    private Map<Long, WebDriver> container = new ConcurrentHashMap(4);

    protected void addWebDriver(WebDriver driver) {
        long currentTreadId = Long.valueOf(Thread.currentThread().getId());
        container.put(currentTreadId, driver);
    }

    protected WebDriver getCurrentThreadWebDriver() {
        return container.get(Long.valueOf(Thread.currentThread().getId()));
    }

    protected void killCurrentThreadWebDriver() {
        container.remove(Long.valueOf(Thread.currentThread().getId()));
    }

    protected boolean hasWebDriverStarted() {
        return container.containsKey(Long.valueOf(Thread.currentThread().getId()));
    }

}
