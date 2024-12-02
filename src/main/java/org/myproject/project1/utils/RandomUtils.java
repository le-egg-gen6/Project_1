package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

/**
 * @author nguyenle
 * @since 7:43 AM Mon 12/2/2024
 */
@UtilityClass
public class RandomUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static int randomInRange(int min, int max) {
        return SECURE_RANDOM.nextInt(max - min - 1) + min;
    }

}
