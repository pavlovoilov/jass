package com.jass.core.handlers;

import com.jass.core.webdrivers.Driver;
import com.jass.utils.CustomProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;
import static com.jass.utils.SleepUtils.sleep;

public class NavigationHandler {

    private static Logger log = LoggerFactory.getLogger(NavigationHandler.class);

    public static void open(String url) {
        log.info("Opening page: " + url);
        if (Driver.isSafari() && DRIVER().getCurrentUrl().equals(url)) {
            refreshPage();
        } else if (Driver.isIE()) {
            DRIVER().get(url);
            refreshPage();
        } else {
            DRIVER().get(url);
        }
    }

    public static void refreshPage() {
        log.info("Refreshing page: " + DRIVER().getCurrentUrl());
        DRIVER().navigate().refresh();
    }

    public static void refreshPageWithDelayAfter(long delayMs) {
        refreshPage();
        sleep(delayMs);
    }

    public static void goBack() {
        log.info("Navigating back from page: " + DRIVER().getCurrentUrl());
        DRIVER().navigate().back();
        log.info("Opened page: " + DRIVER().getCurrentUrl());
    }

    public static void openPage(String link) {
        open(CustomProperties.getDefaultServer() + link);
    }

//    public static <T extends BaseAbstractPage> T openPage(Class<T> pageClass) {
//        return openPage(pageClass, "");
//    }
//
//    public static <T extends BaseAbstractPage> T openPage(Class<T> pageClass, String... params) {
//        open(getLinkOfPage(pageClass, params));
//        try {
//            return pageClass.newInstance();
//        } catch (InstantiationException | IllegalAccessException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}