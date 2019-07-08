package com.thecodinginterface.randomnumber.utils;

import java.util.Random;

public class RandomNumberUtils {

    private static Random random = new Random();

    public static int boundedRandomNumber(int lowerBounds, int upperBounds) {
        return random.nextInt((upperBounds - lowerBounds) + 1) + lowerBounds;
    }
}