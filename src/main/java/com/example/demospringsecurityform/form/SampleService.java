package com.example.demospringsecurityform.form;

import com.example.demospringsecurityform.account.Account;
import com.example.demospringsecurityform.account.AccountContext;
import com.example.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sun.plugin.liveconnect.SecurityContextHelper;

@Service
public class SampleService {
    @Secured("ROLE_USER") //메서드 호출 전 권한 검사
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal(); //principal은 object type이기 떄문에 UserDetails 또는 USer로 타입변환.
        System.out.println("=========");
        System.out.println(userDetails.getUsername());
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called");
    }
}
