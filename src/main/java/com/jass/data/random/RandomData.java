package com.jass.data.random;

import java.util.Random;

public class RandomData {

    public static String randomInt(int exponent) {
        return new StringBuilder().append("_").append(new Random().nextInt(exponent)).toString();
    }

    public static String randomInt() {
        return randomInt(10*5);
    }
}
