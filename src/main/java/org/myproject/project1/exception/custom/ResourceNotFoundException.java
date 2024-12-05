package org.myproject.project1.exception.custom;

/**
 * @author nguyenle
 * @since 1:27 AM Fri 12/6/2024
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
