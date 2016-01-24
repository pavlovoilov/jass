package com.jass.core.handlers;

import org.openqa.selenium.By;

public class LocatorBuilder {

    public static By buildCSS(String selector, String text) {
        return By.cssSelector(String.format(selector, text));
    }

    public static By buildXpath(String xpathExpression) {
        return By.xpath(xpathExpression);
    }

    public static By buildXpath(String xpathExpression, String text) {
        return By.xpath(String.format(xpathExpression, text));
    }

    public static By buildXpath(String xpathExpression, Object text) {
        return By.xpath(String.format(xpathExpression, text.toString()));
    }

    public static By buildXpath(String xpathExpression, Object[] args) {
        return By.xpath(String.format(xpathExpression, args));
    }

    public static By buildXpath(String xpathExpression, String... texts) {
        return By.xpath(String.format(xpathExpression, texts));
    }
}
