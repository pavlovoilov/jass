package com.jass.core.elements;

import com.jass.core.handlers.JSHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.utils.SleepUtils.sleep;

public class Checkbox extends Element {


    public Checkbox(String stringLocator) {
        super(stringLocator);
    }

    public Checkbox(By relativeLocator) {
        super(relativeLocator);
    }

    public Checkbox(WebElement webElement) {
        super(webElement);
    }

    public boolean isSelected() {
        waitUntilDisplayedAndAssert(this);
        return this.find().isSelected();
    }

    /**
     * General method for checkboxes to set checked/unchecked state
     * @param state - boolean true or false that corresponds to checked or unchecked state
     */
    public void setCheckbox(boolean state) {
        waitUntilDisplayedAndAssert(this);
        int counter = 0;
        while (counter < 10) {
            if (this.isSelected() != state) {
                this.find().click();
                counter += 1;
                sleep(500);
            } else {
                return;
            }
        }
        throw new AssertionError("Checkbox was not set to desired state!");
    }

    /**
     * Works only with CSS selectors
     */
    public boolean isSelectedByJS() {
        waitUntilDisplayedAndAssert(this);
        return (boolean) JSHandler.executeJavaScript("return $(arguments[0]).prop('checked');", this.find());
    }
}
