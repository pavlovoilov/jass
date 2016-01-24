package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class InputField extends Element {

    public InputField(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public InputField(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public InputField(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public InputField(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public InputField(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
    }

    public void type(String text) {
        waitUntilDisplayedAndAssert(this);
        this.find().clear();
        this.find().sendKeys(text);
    }

    public void typeAndPressReturn(String text) {
        type(text + Keys.RETURN);
    }

    public void setValue(String text) {
        waitUntilDisplayedAndAssert(this);
        ((JavascriptExecutor) DRIVER()).executeScript("arguments[0].value = '" + text + "';", this.find());
    }
}
