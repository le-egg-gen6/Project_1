package org.myproject.project1.config.security.user;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.db.AccountService;
import org.myproject.project1.db.DBAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 3:02 AM Thu 12/5/2024
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DBAccount account = accountService.getByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(account);
    }

}
