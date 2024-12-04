package org.myproject.project1.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nguyenle
 * @since 12:03 AM Thu 12/5/2024
 */
@Repository
public interface AccountRepository extends MongoRepository<DBAccount, String> {

    Optional<DBAccount> findByUsername(String username);

    Optional<DBAccount> findByEmail(String email);

}
