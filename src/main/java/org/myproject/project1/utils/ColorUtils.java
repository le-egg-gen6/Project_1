package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;

/**
 * @author nguyenle
 * @since 7:40 AM Mon 12/2/2024
 */
@UtilityClass
public class ColorUtils {

    public static String generateNodeColor() {
        int code1 = RandomUtils.randomInRange(0, 255);
        int code2 = RandomUtils.randomInRange(0, 255);
        int code3 = RandomUtils.randomInRange(0, 255);
        return "#" + String.format("%X%X%X", code1, code2, code3);
    }

}
