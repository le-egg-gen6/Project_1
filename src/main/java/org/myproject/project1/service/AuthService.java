package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 7:02 AM Mon 12/2/2024
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JedisService jedisService;


}
