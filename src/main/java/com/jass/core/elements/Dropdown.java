package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class Dropdown extends Element{

    public Dropdown(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public Dropdown(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public Dropdown(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public Dropdown(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public Dropdown(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
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
