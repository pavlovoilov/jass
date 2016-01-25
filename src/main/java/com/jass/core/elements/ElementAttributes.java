package com.jass.core.elements;

public class ElementAttributes {

    public static String getAttribute(Element element, String attribute) {
        return element.find().getAttribute(attribute).trim();
    }

    public static String getValue(Element element) {
        return element.find().getAttribute("value").trim();
    }

    public static boolean isClassAttributeHasActive(Element element) {
        return element.find().getAttribute("class").contains("active");
    }

    public static boolean isClassContains(Element element, String value) {
        return element.find().getAttribute("class").contains(value);
    }
}
