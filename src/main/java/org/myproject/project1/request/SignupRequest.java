package org.myproject.project1.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nguyenle
 * @since 7:29 AM Mon 12/2/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

}
