package com.wsx.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wsx.example.security.CustomUserService;

/**
 * 
 * @author wsx
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserService();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService());//user Details Service验证
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll()//登录页面用户任意访问
				.and()
				.logout().permitAll();//注销行为任意访问
	}
}
