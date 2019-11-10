package com.example.demospringsecurityform.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                        .loginPage("/login")  //login페이지 커스터마이징하면 defaultLogin/LogoutPageGeneratingFilter가 등록 안된다. default설정을 안쓴다는 뜻
                        .permitAll();

                http.httpBasic();

                http.logout()
                            //.logoutUrl("/logout") //logout 처리하는 url을 설정함.
                            .logoutSuccessUrl("/"); //logout후 처리되는 Url

                http.sessionManagement()
                    .maximumSessions(1);  //동시성 제어. 한 계정 동시 접속 1명으로 제한


                http.exceptionHandling()
                       // .accessDeniedPage("/access-denied");  // 페이지에서 인증이 거부 되었을 때 보여주는 페이지. default는 화이트 에러 페이지.. 이렇게하면 로그를 확인하지 못함.
                         .accessDeniedHandler(new AccessDeniedHandler() { //accessDeniedHandler를 사용하면 아래와 같이 에러로그도 남기고 페이지로 넘길 수 도 있음
                             @Override
                             public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                                UserDetails principal =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                                String username = principal.getUsername();
                                 System.out.println(username + "is denied to access"+httpServletRequest.getRequestURI());
                                 httpServletResponse.sendRedirect("/access-denied");
                             }
                         });


        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL); //현재 쓰레드안의 하위 쓰레드에까지 씨큐리티컨텍스트를 공유하는 전략 for @Async

    }
}
