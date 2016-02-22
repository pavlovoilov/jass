package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.jass.core.handlers.JSHandler.executeJavaScript;
import static com.jass.core.handlers.LocatorBuilder.buildXpath;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class Element {

    private static final int DEFAULT_TIMEOUT = 5000;

    private By baseLocator = By.xpath("/html");
    private By relativeLocator;
    private String stringLocator;
    private String elementDescription = "Element";
    private int waitTimeout = DEFAULT_TIMEOUT;
    private WebElement webElement;

    public Element(String stringLocator) {
        this.stringLocator = stringLocator;
    }

    public Element(By relativeLocator) {
        this.relativeLocator = relativeLocator;
    }

    public Element(WebElement webElement) {
        this.webElement = webElement;
    }

    public <T extends Element> T setBaseLocator(By baseLocator) {
        this.baseLocator = baseLocator;
        return (T) this;
    }

    public <T extends Element> T setRelativeLocator(By relativeLocator) {
        this.relativeLocator = relativeLocator;
        return (T) this;
    }

    public <T extends Element> T setStringLocator(String stringLocator) {
        this.stringLocator = stringLocator;
        return (T) this;
    }

    public <T extends Element> T setDescription(String elementDescription) {
        this.elementDescription = elementDescription;
        return (T) this;
    }

    public <T extends Element> T setTimeout(int waitTimeout) {
        this.waitTimeout = waitTimeout;
        return (T) this;
    }

    public String getDescription() {
        return elementDescription;
    }

    public int getTimeout() {
        return waitTimeout;
    }

    public WebElement getWebElement() {
        return find();
    }

    public <T extends Element> T format(Object... object) {
        relativeLocator = buildXpath(stringLocator, object);
        elementDescription = String.format(elementDescription, object);
        return (T) this;
    }

    public WebElement find() {
        if (webElement == null) {
            return DRIVER().findElement(baseLocator).findElement(relativeLocator);
        }
        return webElement;
    }

    public List<Element> findAll() {
        List<WebElement> webElements = DRIVER().findElement(baseLocator).findElements(relativeLocator);
        List<Element> elementsList = new ArrayList<>();
        for (WebElement webElement: webElements) {
            elementsList.add(new Element(webElement));
        }
        return elementsList;
    }

    public <T extends Element> T getFirstDisplayedElement() {
        for (Element element: findAll()) {
            if (element.isDisplayed()) {
                return (T) element;
            }
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

    public <T extends Element> T scrollToElementBottom() {
        try {
            executeJavaScript("arguments[0].scrollIntoView();", this.find());
        } catch (WebDriverException e) {
            // Do nothing
        }
        return (T) this;
    }

    public <T extends Element> T scrollIntoView() {
        try {
            executeJavaScript("arguments[0].scrollIntoView(false);", this.find());
        } catch (WebDriverException e) {
            // Do nothing
        }
        return (T) this;
    }
}
