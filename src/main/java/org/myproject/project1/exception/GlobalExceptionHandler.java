package org.myproject.project1.exception;

import jakarta.annotation.Priority;
import org.myproject.project1.exception.custom.AuthenticationException;
import org.myproject.project1.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author nguyenle
 * @since 10:58 PM Thu 12/5/2024
 */
@ControllerAdvice
@Priority(Integer.MIN_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(AuthenticationException ex, final WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, final WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }

}
