/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.services;

import com.egg.news.entities.AdminUser;
import com.egg.news.entities.NewUser;
import com.egg.news.myexception.MyException;
import com.egg.news.newsenums.Rol;
import com.egg.news.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ayala gonzalo
 */
@Service
public class AdminService implements UserDetailsService{
    
    @Autowired
    private UserRepository AdminRepository;
	
	@Transactional
	public void createAdminUser(String uName, String password, String repeatPassword,String email) throws MyException{
		
            
            validateUser(uName, password, repeatPassword, email);
            
            NewUser user = new AdminUser();
            
            user.setUserName(uName);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            
            user.setRol(Rol.ADMIN);
            
            user.setEmail(email);
            
            user.setRegisterDate(new Date());
            
            AdminRepository.save(user);
            
	
	}
	
        public void validateUser(String uName, String password, String repeatPassword, String email)throws MyException{
        
            if(uName.isEmpty() || uName.isBlank()){
            
                throw new MyException("User name can't be empty");
                
            }
            
            if(password.isEmpty() || password.isBlank() || password.length() <= 5){
            
                throw new MyException("Please enter a password and must be more than 5 characters");
            }
            
            if(repeatPassword.isEmpty() || repeatPassword.isBlank()){
            
                throw new MyException("Please repeat password");
                
            }
            
            if(!repeatPassword.equals(password)){
            
                throw new MyException("You must repeat the password correctly!");
            
            }
            
            if(email.isEmpty() || email.isBlank()){
            
                throw new MyException("email can't be empty");
                
            }
			
			if(!email.contains("@") && !email.contains(".com")){
            
                throw new MyException("email not allowed");
            
            }
            
        }

    @Override
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {
    
        NewUser admin = AdminRepository.findByUName(uName);
        
        if(admin != null){
        
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + admin.getRol().toString());
            
            permisos.add(p);
            
            User user = new User(admin.getUserName(), admin.getPassword(), permisos);
            
           
          return user;
        }else {
            return null;
                    
        }
       
        
        
    }
    
    
    
}
