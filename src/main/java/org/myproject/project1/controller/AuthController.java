package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.request.LoginRequest;
import org.myproject.project1.request.SignupRequest;
import org.myproject.project1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author nguyenle
 * @since 7:01 AM Mon 12/2/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return null;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(
            @RequestBody SignupRequest signupRequest
    ) {
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

}
