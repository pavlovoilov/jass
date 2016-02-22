package com.jass.core.elements;

import com.jass.core.handlers.JSHandler;
import com.jass.core.webdrivers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.jass.core.elements.Waiter.waitUntilDisplayedAndAssert;
import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

/**
 * Element for cases when drag-and-drop needed
 */
public class Place extends Element {

    public Place(String stringLocator) {
        super(stringLocator);
    }

    public Place(By relativeLocator) {
        super(relativeLocator);
    }

    public Place(WebElement webElement) {
        super(webElement);
    }

    public void dragFromTo(Element fromItem, Element toItem) {
        waitUntilDisplayedAndAssert(fromItem);
        waitUntilDisplayedAndAssert(toItem);
        WebElement from = fromItem.find();
        WebElement to = toItem.find();
        if (Driver.isSafari()){
            String xto=Integer.toString(to.getLocation().x);
            String yto=Integer.toString(to.getLocation().y);
            JSHandler.executeJavaScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
                            "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                    from, xto, yto);
        } else {
            Actions actions = new Actions(DRIVER());
            actions.dragAndDrop(from, to);
            actions.build().perform();
        }
    }
}
