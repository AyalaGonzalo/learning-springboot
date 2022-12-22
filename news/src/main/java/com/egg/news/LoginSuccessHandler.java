/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news;

/**
 *
 * @author Ayala gonzalo
 */

 

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
 
@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException{
    
    
        String targetUrl =  determineTargetUrl(authentication);
        
        if(response.isCommitted()){
        
            return;
        
        }
        
        RedirectStrategy redirectStr = new DefaultRedirectStrategy();
        
        redirectStr.sendRedirect(request, response, targetUrl);
    
    }
    
    
    
    public String determineTargetUrl(Authentication authentication){
    
        String url = "/login";
        
       
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        
        for(GrantedAuthority a : authorities){
        
            roles.add(a.getAuthority());
            System.out.println(roles.toString());
            
        }
        
        if(roles.contains("ROLE_ADMIN") || roles.contains("ROLE_JOURNALIST")){
        
            url="/admin/tools";
        
        }else if(roles.contains("ROLE_USER")){
        
            url="/index-2";
        
        }
        
        return url;
    
    }
}