package com.example.demospringsecurityform.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void sigupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("_csrf")));

    }


    @Test
    public void processSignUp() throws Exception{
        mockMvc.perform(post("/signup")
                .param("username","055055")
                .param("password","123")
                .with(csrf())) //csrf 같이 전송되는 코드.
                .andDo(print())
                .andExpect(status().is3xxRedirection());

    }
}