package com.jass.core.elements;

import org.openqa.selenium.By;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class RadioButton extends Element {


    public RadioButton(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public RadioButton(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public RadioButton(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public RadioButton(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public RadioButton(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
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
