package com.jass.core.elements;

public class ElementAttributes {

    public static String getAttribute(Element element, String attribute) {
        return element.find().getAttribute(attribute).trim();
    }

    public static String getValue(Element element) {
        return element.find().getAttribute("value").trim();
    }
}
