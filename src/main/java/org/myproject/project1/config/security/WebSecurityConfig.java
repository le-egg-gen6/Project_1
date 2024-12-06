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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/auth/logout")).authenticated()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private static String[] ALLOWED_ORIGIN = {
        "http://localhost:5173", //FE test
    };

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALLOWED_ORIGIN)
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .maxAge(3600);
            }
        };
    }

}
