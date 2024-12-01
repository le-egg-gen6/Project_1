package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author nguyenle
 * @since 3:31 AM Mon 12/2/2024
 */
@UtilityClass
public class SecurityContextUtils {

    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
