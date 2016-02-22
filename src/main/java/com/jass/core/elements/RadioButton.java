package com.jass.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class RadioButton extends Element {


    public RadioButton(String stringLocator) {
        super(stringLocator);
    }

    public RadioButton(By relativeLocator) {
        super(relativeLocator);
    }

    public RadioButton(WebElement webElement) {
        super(webElement);
    }

    public void select() {
        waitUntilDisplayedAndAssert(this);
        this.find().click();
    }

    public boolean isSelected() {
        waitUntilDisplayedAndAssert(this);
        return this.find().isSelected();
    }
}
