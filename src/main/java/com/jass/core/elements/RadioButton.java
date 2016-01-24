package com.jass.core.elements;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;

public class RadioButton {

    private Element element;

    protected RadioButton(Element element) {
        this.element = element;
    }

    public void click() {
        waitUntilDisplayedAndAssert(element);
        element.find().click();
    }
}
