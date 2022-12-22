/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news;

/**
 *
 * @author Ayala gonzalo
 */

import com.egg.news.services.AdminService;
import com.egg.news.services.UserService;
import com.egg.news.services.JournalistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
    @Autowired
    public UserService userService;
    
    @Autowired
    public AdminService adminService;
	
    @Autowired
    public JournalistService journalistService;
	
    @Autowired
    public LoginSuccessHandler loginSuccessHandler;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    
    
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(adminService)
		.passwordEncoder(new BCryptPasswordEncoder());
	auth.userDetailsService(journalistService)
		.passwordEncoder(new BCryptPasswordEncoder());
    
    }
       
    
        @Override
	public void configure(HttpSecurity http) throws Exception{
		
		http
                        .authorizeRequests()
                                .antMatchers("/admin/**").hasAnyRole("ADMIN", "JOURNALIST")
                                .antMatchers("/css/*","/js/*","/img/*","/*")
                                .permitAll()
                        .and().formLogin()
                                .loginPage("/sign-in")
				.loginProcessingUrl("/login-success")
                                .usernameParameter("uName")
                                .passwordParameter("password")
                                .successHandler(loginSuccessHandler)
                                .permitAll()
                        .and().logout()
                                .logoutUrl("/log-out")
                                .logoutSuccessUrl("/sign-in")
                                .permitAll()
                        .and().csrf()
				.disable();
		
	}
	
	
	
}