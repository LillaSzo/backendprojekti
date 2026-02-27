package com.wordapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder passwordEndvoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(
				authorize -> authorize
				.requestMatchers("/login").permitAll()
				.requestMatchers("/signup/**").permitAll()
				.requestMatchers("/saveuser").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated())
				.headers(headers -> 
					headers.frameOptions(frameOptions -> frameOptions 
						.disable())) 
				.formLogin(formlogin -> 
					formlogin.loginPage("/login")
					.defaultSuccessUrl("/main", true)
					.permitAll())
				.logout(logout -> logout.permitAll())
				.csrf(csrf->csrf.disable());
				
		return http.build();
	}


}
