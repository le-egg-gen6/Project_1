package org.myproject.project1.exception.custom;

/**
 * @author nguyenle
 * @since 1:54 AM Fri 12/6/2024
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
