package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class InputField extends Element {

    public InputField(String stringLocator) {
        super(stringLocator);
    }

    public InputField(By relativeLocator) {
        super(relativeLocator);
    }

    public InputField(WebElement webElement) {
        super(webElement);
    }

    public void type(String text) {
        waitUntilDisplayedAndAssert(this);
        this.find().clear();
        this.find().sendKeys(text);
    }

    public void typeAndPressReturn(String text) {
        type(text + Keys.RETURN);
    }

    public void setValueByJS(String text) {
        waitUntilDisplayedAndAssert(this);
        ((JavascriptExecutor) DRIVER()).executeScript("arguments[0].value = '" + text + "';", this.find());
    }
}
