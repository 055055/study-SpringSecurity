package com.example.demospringsecurityform.account;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountReposiroty extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);
}
