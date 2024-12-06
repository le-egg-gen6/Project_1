package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.jwt.JwtTokenService;
import org.myproject.project1.config.security.user.UserCredentials;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.db.AccountService;
import org.myproject.project1.db.DBAccount;
import org.myproject.project1.exception.custom.AuthenticationException;
import org.myproject.project1.exception.custom.ResourceUsedException;
import org.myproject.project1.request.LoginRequest;
import org.myproject.project1.request.SignupRequest;
import org.myproject.project1.response.AuthResponse;
import org.myproject.project1.utils.RandomUtils;
import org.myproject.project1.utils.SecurityContextUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final MailService mailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtTokenService.generateJwtToken(account));
        authResponse.setValidated(account.isValidated());

        return authResponse;

    }

    private boolean isSignupRequestValid(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String email = signupRequest.getEmail();
        DBAccount existedWithUsernameAccount = accountService.getByUsername(username);
        DBAccount existedWithEmailAccount = accountService.getByEmail(email);
	    return existedWithEmailAccount == null && existedWithUsernameAccount == null;
    }

    public AuthResponse signup(SignupRequest signupRequest) {
        if (!isSignupRequestValid(signupRequest)) {
            throw new ResourceUsedException("Username or email in use");
        }
        DBAccount account = new DBAccount();
        account.setUsername(signupRequest.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        account.setEmail(signupRequest.getEmail());
        account.setVerificationCode(RandomUtils.randomValidationToken());
        account = accountService.save(account);

        mailService.sendVerificationToken(account);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtTokenService.generateJwtToken(account));
        authResponse.setValidated(account.isValidated());

        return authResponse;
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
