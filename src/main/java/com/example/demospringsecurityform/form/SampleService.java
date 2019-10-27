package com.example.demospringsecurityform.form;

import com.example.demospringsecurityform.account.Account;
import com.example.demospringsecurityform.account.AccountContext;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    public void dashboard() {
        Account account = AccountContext.getAccount();
        System.out.println("=========");
        System.out.println(account.getUsername());
    }
}
