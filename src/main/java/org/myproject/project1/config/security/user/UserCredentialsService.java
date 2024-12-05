package org.myproject.project1.config.security.user;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.myproject.project1.db.DBAccount;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author nguyenle
 * @since 2:44 AM Fri 12/6/2024
 */
@Service
public class UserCredentialsService {

    private Cache<String, UserCredentials> cache = Caffeine.newBuilder()
            .maximumSize(1_000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    public UserCredentials getUserCredentials(String token) {
        UserCredentials userCredentials = cache.getIfPresent(token);
        if (userCredentials == null) {
            userCredentials = new UserCredentials();
            userCredentials.setToken(token);
            cache.put(token, userCredentials);
        }
        return userCredentials;
    }

}
