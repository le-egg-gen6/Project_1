package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.jwt.JwtTokenService;
import org.myproject.project1.config.security.user.UserCredentials;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.db.AccountService;
import org.myproject.project1.db.DBAccount;
import org.myproject.project1.exception.custom.AuthenticationException;
import org.myproject.project1.request.LoginRequest;
import org.myproject.project1.response.AuthResponse;
import org.myproject.project1.utils.SecurityContextUtils;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 7:02 AM Mon 12/2/2024
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JedisService jedisService;

    private final AccountService accountService;

    private final JwtTokenService jwtTokenService;

    public AuthResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        DBAccount account = accountService.getByUsername(username);
        if (account == null) {
            throw new AuthenticationException("Account not found");
        }
        if (!password.equals(account.getPassword())) {
            throw new AuthenticationException("Password or username not match");
        }

    }

    public void logout() {
        UserDetailsImpl userDetails = SecurityContextUtils.getCurrentUserDetails();
        UserCredentials userCredentials = SecurityContextUtils.getCurrentUserCredentials();
        if (userDetails == null || userCredentials == null) {
            throw new AuthenticationException("You are not logged in");
        }
        String tokenJwt = userCredentials.getToken();
        jedisService.save(tokenJwt);
    }

}
