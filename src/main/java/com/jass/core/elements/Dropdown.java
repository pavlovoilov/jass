package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class Dropdown extends Element{

    public Dropdown(String stringLocator) {
        super(stringLocator);
    }

    public Dropdown(By relativeLocator) {
        super(relativeLocator);
    }

    public Dropdown(WebElement webElement) {
        super(webElement);
    }

    public void selectByVisibleText(String visibleText) {
        waitUntilDisplayedAndAssert(this);
        new Select(this.find()).selectByVisibleText(visibleText);
    }

    public void selectByValue(String value) {
        waitUntilDisplayedAndAssert(this);
        new Select(this.find()).selectByValue(value);
    }

    public String getSelectedValue() {
        waitUntilDisplayedAndAssert(this);
        return new Select(this.find()).getFirstSelectedOption().getText().trim();
    }
}
