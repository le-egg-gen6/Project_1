package org.myproject.project1.config.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author nguyenle
 * @since 2:57 AM Thu 12/5/2024
 */
@Configuration
@Getter
public class SecurityConstant {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expireTime}")
    private long expiredTimeInMillis;

}
