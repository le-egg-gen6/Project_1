package org.myproject.project1.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nguyenle
 * @since 12:01 AM Thu 12/5/2024
 */
@Document(collection = "c_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBAccount {

    @Id
    private String id;

    @Indexed(background = true)
    private String username;

    @Indexed(background = true)
    private String email;

    private String firstName;

    private String lastName;

    private String verificationCode;

    private String password;

    private boolean validated = false;

}
