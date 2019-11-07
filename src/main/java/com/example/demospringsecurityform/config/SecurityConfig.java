package com.example.demospringsecurityform.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityExpressionHandler expressionHandler(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
       // web.ignoring().mvcMatchers("/favicon.ico"); //스프링 시큐리티 favicon요청에 대해  필터  아무것도 적용안함.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //springboot가 staticResources들에 대해 필터 무시 적용
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .mvcMatchers("/","/info","/account/**","/signup").permitAll()
                    .mvcMatchers("/admin").hasRole("ADMIN")
                    .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .expressionHandler(expressionHandler());

                http.formLogin()
                        //.usernameParameter("my-username") //username 변수명 변경
                        //.passwordParameter("my-password") //password 변수명 변경
                        //.loginPage("/signin")  //login페이지 커스터마이징하면 defaultLogin/LogoutPageGeneratingFilter가 등록 안된다. default설정을 안쓴다는 뜻
                ;
                http.httpBasic();

                http.logout()
                            //.logoutUrl("/logout") //logout 처리하는 url을 설정함.
                            .logoutSuccessUrl("/"); //logout후 처리되는 Url


        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL); //현재 쓰레드안의 하위 쓰레드에까지 씨큐리티컨텍스트를 공유하는 전략 for @Async

    }
}
