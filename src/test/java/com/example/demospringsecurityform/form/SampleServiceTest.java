package com.example.demospringsecurityform.form;

import com.example.demospringsecurityform.account.Account;
import com.example.demospringsecurityform.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void dashboard(){

        Account account = new Account();
        account.setRole("USER");
        account.setUsername("055055");
        account.setPassword("123");
        accountService.createNew(account);
        UserDetails userDetails = accountService.loadUserByUsername("055055");

        //코드를 통해 인증받은 객체 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,"123");
        Authentication authentication =authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }
}