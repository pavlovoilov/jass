package com.jass.core.handlers;

import com.jass.core.elements.Element;
import com.jass.core.elements.JSElement;
import org.openqa.selenium.JavascriptExecutor;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class JSHandler {

    public static <T> T executeJavaScript(String jsCode, Object... arguments) {
        return (T) ((JavascriptExecutor) DRIVER()).executeScript(jsCode, arguments);
    }

    public static void setAttributeThroughJavascript(String attribute, String value, Element element) {
        waitUntilDisplayedAndAssert(element);
        executeJavaScript("arguments[0].setAttribute(\"" + attribute + "\",\"" + value + "\")", element.find());
    }

    public JSElement getElementById(String locator) {
        return new JSElement(String.format("document.getElementById('%s')", locator));
    }

    public JSElement getElementByName(String locator) {
        return new JSElement(String.format("document.getElementsByName('%s')[0]", locator));
    }

    public JSElement getElementByClassName(String locator) {
        return new JSElement(String.format("document.getElementsByClassName('%s')[0]", locator));
    }

    public JSElement getElementByTagName(String locator) {
        return new JSElement(String.format("document.getElementsByTagName('%s')[0]", locator));
    }
}
