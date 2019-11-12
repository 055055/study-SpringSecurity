package com.example.demospringsecurityform.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired AccountReposiroty accountReposiroty;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountReposiroty.findByUsername(username);
        if (account == null) {
             throw new UsernameNotFoundException(username);
        }
      return new UserAccount(account);

    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return accountReposiroty.save(account);
    }
}
