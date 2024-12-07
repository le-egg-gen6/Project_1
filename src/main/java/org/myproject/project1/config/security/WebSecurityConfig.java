package org.myproject.project1.config.security;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.jwt.AuthTokenFilter;
import org.myproject.project1.config.jwt.EmailVerificationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author nguyenle
 * @since 3:33 AM Mon 12/2/2024
 */
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthTokenFilter authTokenFilter;

    private final EmailVerificationFilter emailVerificationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final DaoAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .anyRequest().permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/verify/**")).authenticated()
//                                .anyRequest().hasAnyAuthority("EMAIL_VERIFIED")
                );

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterAfter(emailVerificationFilter, AuthTokenFilter.class);

        return http.build();
    }

}
