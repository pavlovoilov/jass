package com.jass.utils;

public class SleepUtils {

    /**
     * sleep method for usage in wait activities
     * @param sleepTime - in milliseconds
     */
    public static void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
