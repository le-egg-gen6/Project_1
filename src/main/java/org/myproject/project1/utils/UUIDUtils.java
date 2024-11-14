package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author nguyenle
 * @since 10:52 PM Tue 11/12/2024
 */
@UtilityClass
public class UUIDUtils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
