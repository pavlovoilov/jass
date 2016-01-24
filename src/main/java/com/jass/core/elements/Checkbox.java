package com.jass.core.elements;

import org.openqa.selenium.By;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class Checkbox extends Element {

    public Checkbox(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public Checkbox(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public Checkbox(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public Checkbox(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public Checkbox(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
    }

    public boolean isSelected() {
        waitUntilDisplayedAndAssert(this);
        return this.find().isSelected();
    }
}
