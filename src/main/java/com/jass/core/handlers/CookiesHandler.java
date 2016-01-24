package com.jass.core.handlers;

import org.openqa.selenium.Cookie;

import java.util.Set;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class CookiesHandler {

    public static void deleteAllCookies() {
        DRIVER().manage().deleteAllCookies();
    }

    public static Set<Cookie> getCookies() {
        return DRIVER().manage().getCookies();
    }

    public static Cookie getCookieNamed(String cookieName) {
        return DRIVER().manage().getCookieNamed(cookieName);
    }

    public static void deleteCookieNamed(String cookieName) {
        DRIVER().manage().deleteCookieNamed(cookieName);
    }

    public static void addCookie(Cookie cookie) {
        DRIVER().manage().addCookie(cookie);
    }
}
