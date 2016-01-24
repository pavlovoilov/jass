package com.jass.core.handlers;

import com.jass.core.webdrivers.Driver;
import com.jass.utils.CustomProperties;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WebDriverCookieManager {

    private Logger log = LoggerFactory.getLogger(WebDriverCookieManager.class);

    private static final String COOKIE_NAME = "JSESSIONID";
    private String userName;
    private String userPassword;
    private String cookieValue;
    private String sessionIdCookie;
    private String defaultURL = CustomProperties.getDefaultServer();

    public WebDriverCookieManager(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getSessionIdCookie() {
        return sessionIdCookie;
    }

    public void setSessionCookie() {
        boolean sessionInitialized = false;
        int attempt = 1;

        while (!sessionInitialized && attempt < 4) {
            try {
                getPageCookieValue();
                authenticateSessionAndSetNewCookie();

                sessionInitialized = true;
            } catch (Exception | AssertionError e) {
                log.error("Failed to fetch cookie or csrf token from Login Page after " + attempt + " attempt(s)!\n" +
                        "Will try one more time...\n");
                log.warn(e.getStackTrace().toString());

                attempt++;

                CookiesHandler.deleteAllCookies();
            }
        }

        if (!sessionInitialized) {
            throw new AssertionError("Failed to get cookie or csrf token and logged in after " + attempt + " attempts!");
        }
    }

    private void getPageCookieValue() {
        log.info("getPageCookieValue");

        cookieValue = CookiesHandler.getCookieNamed(COOKIE_NAME).getValue();
        log.info("Cookie '" + COOKIE_NAME + "' value is: " + cookieValue);
        if (cookieValue == null) {
            throw new AssertionError(COOKIE_NAME + " cookie is NULL!");
        }
    }

    private void authenticateSessionAndSetNewCookie() {
        log.info("authenticateSessionAndSetNewCookie");
        log.info(String.format("Trying to authenticate with: login=%s; csrf_token=%s;", userName));

        HttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = null;
        try {
            //Execute and get the response.
            response = httpclient.execute(createPostSecurityCheck());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fetch Cookie for next request
        assert response != null;

        log.info("Response on POST security check request\n" + response.toString());

        Header[] header = response.getHeaders("Set-Cookie");
        String rawSessionIdCookie = header[0].getValue();
        rawSessionIdCookie = rawSessionIdCookie.replace("JSESSIONID=", "");
        int index = rawSessionIdCookie.indexOf(";");
        sessionIdCookie = rawSessionIdCookie.substring(0, index);

        if (sessionIdCookie.equals(cookieValue)) {
            throw new AssertionError("'" + COOKIE_NAME + "' was not updated after executing Security Check");
        }

        CookiesHandler.deleteCookieNamed(COOKIE_NAME);
        log.info("Cleaned old cookie '" + COOKIE_NAME + "' with value: " + cookieValue);

        if (Driver.isIE() && CookiesHandler.getCookieNamed(COOKIE_NAME) != null) {
            log.info("Old cookie '" + COOKIE_NAME + "' with value: " + cookieValue + " haven't been deleted on IE browser.Try to delete all cookies.");
            CookiesHandler.deleteAllCookies(); // to force deleting all cookies on IE browser
        }

        CookiesHandler.addCookie(new Cookie(COOKIE_NAME, sessionIdCookie));
        log.info("Set new cookie '" + COOKIE_NAME + "' with value: " + sessionIdCookie);
    }

    private HttpPost createPostSecurityCheck() {
        log.info("createPostSecurityCheck");
        HttpPost httpPost = new HttpPost(defaultURL);

        // Set POST request headers
        httpPost.setHeader("Cookie", COOKIE_NAME + "=" + cookieValue + ";");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("j_username", userName));
        params.add(new BasicNameValuePair("j_password", userPassword));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            //Execute and get the response.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httpPost;
    }

}
