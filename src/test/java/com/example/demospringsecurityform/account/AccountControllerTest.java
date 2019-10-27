package com.example.demospringsecurityform.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    @WithAnonymousUser //anotation 또는 아래 with 2가지 방법으로 요청할 수 있다.
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))//.with(anonymous())) //익명으로 접근했을 때
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser//WithMockUser 반복 코드를 피하기 위해 만든 어노테이션
    //@WithMockUser(username = "055055",roles = "USER")  //anotation 또는 아래 with 2가지 방법으로 요청 가
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))//.with(user("055055").roles("USER")))  //해당 유저로 요청하는게 아니라, 이미 이 유저가 로그인한 상태라는 걸 가정하고 응답이 어떻게 오는지 확인
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "055055",roles = "USER")  //anotation 또는 아래 with 2가지 방법으로 요청 가
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin"))//.with(user("055055").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Transactional
    public void login_success() throws Exception {
        String username = "055055";
        String password = "123";

        Account user = this.createUser(username,password);
        mockMvc.perform(formLogin().user(username).password(password))    // 해당 아이디로 로그인 시도
                .andExpect(authenticated()); //인증이 되는지
    }

    @Test
    @Transactional
    public void login_success2() throws Exception {
        String username = "055055";
        String password = "123";

        Account user = this.createUser(username,password);
        mockMvc.perform(formLogin().user(username).password(password))    // 해당 아이디로 로그인 시도
                .andExpect(authenticated()); //인증이 되는지
    }

    @Test
    public void login_fail() throws Exception {
        String username = "055055";
        String password = "123";

        Account user = this.createUser(username,password);
        mockMvc.perform(formLogin().user(username).password("12345"))    // 해당 아이디로 로그인 시도
                .andExpect(unauthenticated()); //인증이 되는지
    }


    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
