package org.myproject.project1.db;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.ThreadPoolService;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 3:03 AM Thu 12/5/2024
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final ThreadPoolService threadPoolService;

    public DBAccount getAccount(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public DBAccount getByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    public void save(DBAccount account) {
        accountRepository.save(account);
    }

    public void saveAsync(DBAccount account) {
        threadPoolService.submitAsynchronousTask(() -> save(account));
    }

}
