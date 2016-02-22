package com.jass.core.elements;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;
import static com.jass.utils.SleepUtils.sleep;

public class Waiter {

    private static final int DEFAULT_TIMEOUT_MS = 30 * 1000;
    private static final int PULL_UP_INTERVAL_MS = 100;
    private static final int DEFAULT_ELEMENT_WAIT_TIMEOUT = 5000;

    public static Boolean waitUntilElementDisappear(By by) {
        return waitUntil(ExpectedConditions.invisibilityOfElementLocated(by), DEFAULT_TIMEOUT_MS, "waitUntilElementDisappear");
    }

    public static WebElement waitUntilElementAppearVisible(By by) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(by), DEFAULT_TIMEOUT_MS, "waitUntilElementAppearVisible");
    }

    public static <V> V waitUntil(Function<? super WebDriver, V> function) {
        return waitUntil(function, DEFAULT_TIMEOUT_MS, "");
    }

    public static <V> V waitUntil(Function<? super WebDriver, V> function, int CUSTOM_TIMEOUT_MS, String timeoutMessage) {
        Wait<WebDriver> wait = new WebDriverWait(DRIVER(), CUSTOM_TIMEOUT_MS / 1000 , PULL_UP_INTERVAL_MS);
        V result;
        try {
            result = wait.until(function);
        } catch (TimeoutException timeException) {
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntil " + timeoutMessage, timeException.getCause());
        }
        return result;
    }

    public static boolean waitSilentlyUntil(Function<? super WebDriver, Object> function, int CUSTOM_TIMEOUT_MS) {
        Wait<WebDriver> wait = new WebDriverWait(DRIVER(), CUSTOM_TIMEOUT_MS / 1000 , PULL_UP_INTERVAL_MS);
        try {
            wait.until(function);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public static boolean waitUntilDisplayedAndAssert(Element element) {
        return waitUntilDisplayedAndAssert(element, element.getTimeout());
    }

    public static boolean waitUntilDisplayedAndAssert(Element element, int timeout) {
        if (!waitUntilDisplayed(element, timeout)) {
            throw new AssertionError("'" + element.getDescription() + "' element was not displayed after " + timeout + " milliseconds!");
        }
        return true;
    }

    public static boolean waitUntilHiddenAndAssert(Element element) {
        return waitUntilHiddenAndAssert(element, element.getTimeout());
    }

    public static boolean waitUntilHiddenAndAssert(Element element, int timeout) {
        if (!waitUntilHidden(element, timeout)) {
            throw new AssertionError("'" + element.getDescription() + "' element was not hidden after " + timeout + " milliseconds!");
        }
        return true;
    }

    public static boolean waitUntilHidden(Element element) {
        return waitUntilHidden(element, element.getTimeout());
    }

    public static boolean waitUntilHidden(Element element, int timeout) {
        int counter = 1000;
        while (element.isDisplayed() && counter <= timeout && counter != DEFAULT_TIMEOUT_MS) {
            sleep(1000);
            counter += 1000;
        }
        return counter != timeout && counter != DEFAULT_TIMEOUT_MS;
    }

    public static boolean waitUntilDisplayed(Element element) {
        return waitUntilDisplayed(element, element.getTimeout());
    }

    public static boolean waitUntilDisplayed(Element element, int timeout) {
        int counter = 1000;
        while (!element.isDisplayed() && counter <= timeout && counter != DEFAULT_TIMEOUT_MS) {
            sleep(1000);
            counter += 1000;
        }
        return counter != timeout && counter != DEFAULT_TIMEOUT_MS;
    }
}
