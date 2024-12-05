package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 2:35 AM Fri 12/6/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/verify")
public class EmailVerificationController {

    private final MailService mailService;

    @GetMapping("/check")
    public ResponseEntity<?> verifyToken(
            @RequestParam String token
    ) {
        boolean isTokenValid = mailService.verifyAccount(token);
        return ResponseEntity.ok(isTokenValid);
    }

    @GetMapping("/resend")
    public ResponseEntity<?> resendToken() {
        mailService.resendMail();
        return ResponseEntity.ok().build();
    }

}
