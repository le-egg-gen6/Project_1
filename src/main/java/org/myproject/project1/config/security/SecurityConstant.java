package org.myproject.project1.config.security;

import java.util.concurrent.TimeUnit;

/**
 * @author nguyenle
 * @since 2:57 AM Thu 12/5/2024
 */
public class SecurityConstant {

    public static final String JWT_SECRET = "b596538af3a27701ccd99814d55076ab14a7e2eb182dfbfa1d2a933c617538f03d22f0e562e9f4817ad9e9a92597359cbe9dd6bfb86f92e4915c16f5cf967159";

    public static final long JWT_EXPIRATION_IN_MS = TimeUnit.DAYS.toMillis(1); //1 Day

}
