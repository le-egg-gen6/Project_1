package org.myproject.project1.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.security.user.UserCredentials;
import org.myproject.project1.config.security.user.UserCredentialsService;
import org.myproject.project1.exception.CustomFilterExceptionHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author nguyenle
 * @since 12:04 AM Thu 12/5/2024
 */
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UserDetailsService userDetailsService;

    private final UserCredentialsService userCredentialsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = extractToken(request);
            if (jwt != null && jwtTokenService.validateJwtToken(jwt)) {
                String username = jwtTokenService.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UserCredentials userCredentials = userCredentialsService.getUserCredentials(jwt);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userCredentials,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ignored) {
            CustomFilterExceptionHandler.handleUnexpectedExceptionAuthTokenFilter(request, response);
        }
    }

    private String extractToken(@NonNull HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return null;
        }
        String[] parts = token.split(" ");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }

}
