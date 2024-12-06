package org.myproject.project1.config.jwt;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.myproject.project1.config.security.SecurityConfig;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.db.DBAccount;
import org.myproject.project1.service.JedisService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * @author nguyenle
 * @since 2:56 AM Thu 12/5/2024
 */
@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final SecurityConfig securityConfig;

    private final JedisService jedisService;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Create header
        JSONObject header = new JSONObject();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Date current = new Date();
        long tokenExpireTimeInMillis = current.getTime() + securityConfig.getExpiredTimeInMillis();

        // Create payload
        JSONObject payload = new JSONObject();
        payload.put("sub", userPrincipal.getUsername());
        payload.put("iat", current.getTime());
        payload.put("exp", tokenExpireTimeInMillis);

        // Base64 encode header and payload
        String encodedHeader = base64UrlEncode(header.toString());
        String encodedPayload = base64UrlEncode(payload.toString());

        // Create signature
        String signatureInput = encodedHeader + "." + encodedPayload;
        String signature = sign(signatureInput);

        // Combine all parts
        String token = encodedHeader + "." + encodedPayload + "." + signature;
        return token;
    }

    public String generateJwtToken(DBAccount account) {
        // Create header
        JSONObject header = new JSONObject();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Date current = new Date();
        long tokenExpireTimeInMillis = current.getTime() + securityConfig.getExpiredTimeInMillis();

        // Create payload
        JSONObject payload = new JSONObject();
        payload.put("sub", account.getUsername());
        payload.put("iat", current.getTime());
        payload.put("exp", tokenExpireTimeInMillis);

        // Base64 encode header and payload
        String encodedHeader = base64UrlEncode(header.toString());
        String encodedPayload = base64UrlEncode(payload.toString());

        // Create signature
        String signatureInput = encodedHeader + "." + encodedPayload;
        String signature = sign(signatureInput);

        // Combine all parts
        String token = encodedHeader + "." + encodedPayload + "." + signature;
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Verify signature
            String signatureInput = parts[0] + "." + parts[1];
            String expectedSignature = sign(signatureInput);
            if (!expectedSignature.equals(parts[2])) {
                throw new IllegalArgumentException("Invalid signature");
            }

            // Decode payload
            String decodedPayload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject payload = new JSONObject(decodedPayload);

            // Check expiration
            long expiration = payload.getLong("exp");
            if (expiration < new Date().getTime()) {
                throw new IllegalArgumentException("Token has expired");
            }

            return payload.getString("sub");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {
        String tokenFindInLoggedOutTokenStorage = jedisService.find(authToken);
        if (tokenFindInLoggedOutTokenStorage == null || tokenFindInLoggedOutTokenStorage.isEmpty()) {
            return false;
        }
        try {
            String[] parts = authToken.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Verify signature
            String signatureInput = parts[0] + "." + parts[1];
            String expectedSignature = sign(signatureInput);
            if (!expectedSignature.equals(parts[2])) {
                throw new IllegalArgumentException("Invalid signature");
            }

            // Decode payload and verify expiration
            String decodedPayload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject payload = new JSONObject(decodedPayload);
            long expiration = payload.getLong("exp");

            if (expiration < new Date().getTime()) {
                throw new IllegalArgumentException("Token has expired");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String base64UrlEncode(String input) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretBytes = securityConfig.getJwtSecret().getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            mac.init(secretKey);
            byte[] signatureBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error signing JWT", e);
        }
    }

}
