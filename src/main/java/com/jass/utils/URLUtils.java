package com.jass.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class URLUtils {

    public static URL getURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
