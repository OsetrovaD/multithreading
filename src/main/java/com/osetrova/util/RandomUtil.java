package com.osetrova.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int generate(int bound) {
        return RANDOM.nextInt(bound);
    }
}
