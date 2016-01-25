package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.jass.core.handlers.JSHandler.executeJavaScript;
import static com.jass.core.handlers.LocatorBuilder.buildXpath;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class Element {

    private static final int DEFAULT_TIMEOUT = 5000;

    private By baseLocator;
    private By relativeLocator;
    private String stringLocator;
    private String elementDescription;
    private int waitTimeout;

    public Element(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        this.baseLocator = baseLocator;
        this.relativeLocator = relativeLocator;
        this.stringLocator = stringLocator;
        this.elementDescription = elementDescription;
        this.waitTimeout = waitTimeout;
    }

    public Element(By relativeLocator, String elementDescription) {
        this(null, relativeLocator, null, elementDescription, DEFAULT_TIMEOUT);
    }

    public Element(By relativeLocator, String elementDescription, int waitTimeout) {
        this(null, relativeLocator, null, elementDescription, waitTimeout);
    }

    public Element(String stringLocator, String elementDescription, int waitTimeout) {
        this(null, null, stringLocator, elementDescription, waitTimeout);
    }

    public Element(By baseLocator, By relativeLocator, String elementDescription) {
        this(baseLocator, relativeLocator, null, elementDescription, DEFAULT_TIMEOUT);
    }

    public String getDescription() {
        return elementDescription;
    }

    public int getTimeout() {
        return waitTimeout;
    }

    public Element format(Object... object) {
        relativeLocator = buildXpath(stringLocator, object);
        elementDescription = String.format(elementDescription, object);
        return this;
    }

    public WebElement find() {
        return DRIVER().findElement(baseLocator).findElement(relativeLocator);
    }

    public List<WebElement> findAll() {
        return DRIVER().findElement(baseLocator).findElements(relativeLocator);
    }

    public WebElement getFirstDisplayedElement() {
        for (WebElement element: findAll()) {
            if (element.isDisplayed()) return element;
        }
        throw new AssertionError("No visible elements found with such locator!");
    }

    public boolean isDisplayed() {
        try {
            return this.find().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToElementBottom() {
        try {
            executeJavaScript("arguments[0].scrollIntoView();", this.find());
        } catch (WebDriverException e) {
            // Do nothing
        }
    }

    public void scrollIntoView() {
        try {
            executeJavaScript("arguments[0].scrollIntoView(false);", this.find());
        } catch (WebDriverException e) {
            // Do nothing
        }
    }

    public Button asButton() {
        return new Button(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public Checkbox asCheckbox() {
        return new Checkbox(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }
}
