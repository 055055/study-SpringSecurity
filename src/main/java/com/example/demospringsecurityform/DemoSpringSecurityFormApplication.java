package com.example.demospringsecurityform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class DemoSpringSecurityFormApplication {


	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //다양한 암호화 알고리즘을 제공한다. 기본적으로는 bcrypt로 암호화한다.
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSecurityFormApplication.class, args);
	}
}
