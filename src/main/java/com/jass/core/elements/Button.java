package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class Button extends Element {


    public Button(String stringLocator) {
        super(stringLocator);
    }

    public Button(By relativeLocator) {
        super(relativeLocator);
    }

    public Button(WebElement webElement) {
        super(webElement);
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
