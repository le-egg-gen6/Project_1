package org.myproject.project1.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * @author nguyenle
 * @since 5:17 PM Sat 12/7/2024
 */
@UtilityClass
public class CustomFilterExceptionHandler {

    public static void handleUnexpectedExceptionAuthTokenFilter(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write("Something went wrong");
    }

    public static void handleUnexpectedExceptionEmailVerificationFilter(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write("Something went wrong");
    }

}
