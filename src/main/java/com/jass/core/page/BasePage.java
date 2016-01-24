package com.jass.core.page;

import com.jass.core.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.jass.utils.SleepUtils.sleep;

public abstract class BasePage {

    private static Logger log = LoggerFactory.getLogger(BasePage.class);

    public void checkElement() {
        throw new UnsupportedOperationException("Method should be implemented by specific class!");
    }

    public static boolean isOnPage() {
        throw new UnsupportedOperationException("Method should be implemented by specific class!");
    }

    public static void setAttributeThroughJavascript(String attribute, String value, WebElement element) {
        executeJavaScript("arguments[0].setAttribute(\"" + attribute + "\",\"" + value + "\")", element);
    }


    public static void scrollIntoView(By locator) {
        scrollIntoView($(locator));
    }

    public static void scrollIntoView(SelenideElement element) {
        try {
            executeJavaScript("arguments[0].scrollIntoView(false);", element.toWebElement());
        } catch (WebDriverException e) {
            // Do nothing
        }
    }

    public static void scrollToElementBottom(By locator) {
        scrollToElementBottom($(locator));
    }

    public static void scrollToElementBottom(SelenideElement element) {
        try {
            executeJavaScript("arguments[0].scrollIntoView();", element.toWebElement());
        } catch (WebDriverException e) {
            // Do nothing
        }
    }

    /**
     * Works only with CSS selectors
     */
    public static boolean isChecked(SelenideElement checkbox) {
        scrollIntoView(checkbox);
        return (boolean) executeJavaScript("return $(arguments[0]).prop('checked');", checkbox);
    }

    public static boolean isChecked(By checkbox) {
        return isChecked($(checkbox));
    }

    public static boolean isSelected(SelenideElement checkbox) {
        return checkbox.isSelected();
    }

    public static void setCheckbox(By locator, boolean state) {
        setCheckbox($(locator), state);
    }

    /**
     * General method for checkboxes to set checked/unchecked state
     * @param checkbox - Selenide Element for checkbox
     * @param state - boolean true or false that corresponds to checked or unchecked state
     */
    public static void setCheckbox(SelenideElement checkbox, boolean state) {
        int counter = 0;
        while (counter < 10) {
            if (checkbox.isSelected() != state) {
                scrollIntoView(checkbox);
                checkbox.setSelected(state);
                counter += 1;
                if (WebDriverRunner.isIE()) {
                    sleep(2000);
                } else {
                    sleep(500);
                }
            } else {
                return;
            }
        }
        throw new AssertionError("Checkbox was not set to desired state!");
    }


    public static boolean isPageObjectLoaded(Element element) {
        return isPageObjectLoaded(element, element.getTimeout(), element.getDescription());
    }

    public static boolean isPageObjectLoaded(Element element, String errorMessage) {
        return isPageObjectLoaded(element, element.getTimeout(), errorMessage);
    }

    public static boolean isPageObjectLoaded(Element element, int timeout) {
        return isPageObjectLoaded(element, timeout, element.getDescription());
    }

    public static boolean isPageObjectLoaded(Element element, int timeout, String errorMessage) {
        int hangsTimeout = 300000;
        int counter = 0;
        while (!element.isDisplayed() && counter != timeout && counter != hangsTimeout) {
            sleep(1000);
            counter += 1000;
            log.info("Loading page (element) '" + errorMessage + "', counting: " + counter);
        }
        if (counter != timeout && counter != hangsTimeout) {
            log.info("Page (element) '" + errorMessage + "' was loaded!");
            return true;
        } else {
            throw new AssertionError("'" + errorMessage + "' page (element) was not loaded or opened after " + timeout + " milliseconds!");
        }
    }



    public void dragFromTo(By fromItem, By toItem) {
        if (WebDriverRunner.isSafari()){
            SelenideElement from = $(fromItem);
            SelenideElement to = $(toItem);
            String xto=Integer.toString(to.getLocation().x);
            String yto=Integer.toString(to.getLocation().y);
            executeJavaScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
                            "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                    from, xto, yto);
        } else {
            Actions actions = new Actions(WebDriverRunner.getWebDriver());
            actions.dragAndDrop($(fromItem).toWebElement(), $(toItem).toWebElement());
            actions.build().perform();
        }
    }

    public static boolean isClassAttributeHasActive(SelenideElement element) {
        return element.attr("class").contains("active");
    }

    public static boolean isClassContains(SelenideElement element, String value) {
        return element.attr("class").contains(value);
    }

    public static void waitUntilStayActive(SelenideElement element, int second, String assertMessage) {
        boolean isActive = false;
        int count = 0;
        while (!isActive) {
            count++;
            sleep(1000);
            isActive = isClassAttributeHasActive(element);
            if (count > second) {
                throw new AssertionError(assertMessage);
            }
        }
    }

    public static void waitUntilClassValueDisappears(SelenideElement element, int time, String value) {
        boolean containsValue = true;
        int count = 1000;
        while (containsValue) {
            count += 1000;
            sleep(1000);
            containsValue = isClassContains(element, value);
            if (count > time) {
                break;
            }
        }
    }

    public static void waitUntilClassValueAppears(SelenideElement element, int time, String value) {
        boolean containsValue = false;
        int count = 1000;
        while (!containsValue) {
            count += 1000;
            sleep(1000);
            containsValue = isClassContains(element, value);
            if (count > time) {
                break;
            }
        }
    }

}