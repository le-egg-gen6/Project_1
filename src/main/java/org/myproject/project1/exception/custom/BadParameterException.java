package org.myproject.project1.exception.custom;

/**
 * @author nguyenle
 * @since 2:28 PM Sun 12/8/2024
 */
public class BadParameterException extends RuntimeException {
    public BadParameterException(String message) {
        super(message);
    }
}
