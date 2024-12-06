package org.myproject.project1.db;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
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

    private Cache<String, DBAccount> usernameToAccount = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .build();

    private Cache<String, DBAccount> idToAccount = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .build();

    private Cache<String, DBAccount> mailToAccount = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .build();

    private final AccountRepository accountRepository;

    private final ThreadPoolService threadPoolService;

    private void putToCache(DBAccount account) {
        usernameToAccount.put(account.getUsername(), account);
        idToAccount.put(account.getId(), account);
        mailToAccount.put(account.getEmail(), account);
    }

    public DBAccount getAccount(String id) {
        DBAccount account = idToAccount.getIfPresent(id);
        if (account == null) {
            account = accountRepository.findById(id).orElse(null);
            if (account != null) {
                putToCache(account);
            }
        }
        return account;
    }

    public DBAccount getByUsername(String username) {
        DBAccount account = usernameToAccount.getIfPresent(username);
        if (account == null) {
            account = accountRepository.findById(username).orElse(null);
            if (account != null) {
                putToCache(account);
            }
        }
        return account;
    }

    public DBAccount getByEmail(String email) {
        DBAccount account = mailToAccount.getIfPresent(email);
        if (account == null) {
            account = accountRepository.findById(email).orElse(null);
            if (account != null) {
                putToCache(account);
            }
        }
        return account;
    }

    public DBAccount save(DBAccount account) {
        DBAccount savedAccount = accountRepository.save(account);
        putToCache(savedAccount);
        return savedAccount;
    }

    public void saveAsync(DBAccount account) {
        threadPoolService.submitAsynchronousTask(() -> save(account));
    }

}
