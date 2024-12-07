package org.myproject.project1.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author nguyenle
 * @since 2:46 AM Thu 12/5/2024
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

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
