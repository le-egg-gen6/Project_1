package org.myproject.project1.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 3:03 AM Thu 12/5/2024
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public DBAccount getAccount(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public DBAccount getByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

}
