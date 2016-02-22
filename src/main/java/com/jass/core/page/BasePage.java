package com.jass.core.page;

import com.jass.core.elements.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jass.utils.SleepUtils.sleep;

public abstract class BasePage {

    private static Logger log = LoggerFactory.getLogger(BasePage.class);
    private static final int PAGE_LOADING_HANGS_TIMEOUT = 60000;

    public void checkElement() {
        throw new UnsupportedOperationException("Method should be implemented by specific class!");
    }

    public static boolean isOnPage() {
        throw new UnsupportedOperationException("Method should be implemented by specific class!");
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
        int counter = 0;
        while (!element.isDisplayed() && counter <= timeout && counter != PAGE_LOADING_HANGS_TIMEOUT) {
            sleep(1000);
            counter += 1000;
            log.info("Loading page '" + errorMessage + "', counting: " + counter);
        }
        if (counter != timeout && counter != PAGE_LOADING_HANGS_TIMEOUT) {
            log.info("Page '" + errorMessage + "' was loaded!");
            return true;
        } else {
            throw new AssertionError("'" + errorMessage + "' page was not loaded or opened after " + timeout + " milliseconds!");
        }
    }
}