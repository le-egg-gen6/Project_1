package org.myproject.project1.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 7:02 AM Mon 12/2/2024
 */
@Service
@RequiredArgsConstructor
public class JedisService {

    private static final String CACHE_NAME = "AuthToken";

    private final RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(String authToken) {
        hashOperations.put(CACHE_NAME, authToken, authToken);
    }

    public String find(String authToken) {
        return hashOperations.get(CACHE_NAME, authToken);
    }

    public void delete(String authToken) {
        hashOperations.delete(CACHE_NAME, authToken);
    }

}
