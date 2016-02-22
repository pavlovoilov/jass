package com.jass.core.elements;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getTexts(Element element) {
        List<String> resultList = new ArrayList<>();
        element.findAll().forEach(item -> resultList.add(item.find().getText().trim()));
        return resultList;
    }
}
