package com.jass.core.elements;

import org.openqa.selenium.By;

/**
 * Element for cases when drag-and-drop needed
 */
public class Place extends Element {


    public Place(By baseLocator, By relativeLocator, String stringLocator, String elementDescription, int waitTimeout) {
        super(baseLocator, relativeLocator, stringLocator, elementDescription, waitTimeout);
    }

    public Place(By relativeLocator, String elementDescription) {
        super(relativeLocator, elementDescription);
    }

    public Place(By relativeLocator, String elementDescription, int waitTimeout) {
        super(relativeLocator, elementDescription, waitTimeout);
    }

    public Place(String stringLocator, String elementDescription, int waitTimeout) {
        super(stringLocator, elementDescription, waitTimeout);
    }

    public Place(By baseLocator, By relativeLocator, String elementDescription) {
        super(baseLocator, relativeLocator, elementDescription);
    }
}
