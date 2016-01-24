package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class Button extends Element {

    public Button(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public Button(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public Button(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public Button(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public Button(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
    }

    public void click() {
        waitUntilDisplayedAndAssert(this);
        this.find().click();
    }

    public void clickByJs() {
        waitUntilDisplayedAndAssert(this);
        ((JavascriptExecutor) DRIVER()).executeScript("arguments[0].click();", this.find());
    }
}
