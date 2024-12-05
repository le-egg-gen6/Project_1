package org.myproject.project1.config.mail;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author nguyenle
 * @since 2:01 AM Fri 12/6/2024
 */
@Configuration
@Getter
public class MailSenderConfig {

    @Value("${spring.mail.protocol:smtp.gmail.com}")
    private String host;

    @Value("${spring.mail.port:587}")
    private Integer port;

    @Value("${spring.mail.sender}")
    private String sender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean auth;

    @Value("${spring.mail.smtp.starttls.enable:true}")
    private boolean tls;

}
