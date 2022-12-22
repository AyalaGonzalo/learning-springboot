/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.services;

import java.util.Date;
import com.egg.news.entities.Journalist;
import com.egg.news.entities.News;
import com.egg.news.myexception.MyException;
import com.egg.news.newsenums.Rol;
import com.egg.news.repository.JournalistRepository;
import com.egg.news.repository.NewsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ayala gonzalo
 */
@Service
public class JournalistService implements UserDetailsService{
    
    @Autowired
    private JournalistRepository journalistRepo;
	
	@Autowired
	private NewsRepository newsRepo;
	
	/*

	
	*/
    
    @Transactional
    public void createJournalist(String uName, String password, String repeatPassword, String email, int salary) throws MyException{
    
        validateUser(uName, password, repeatPassword, email, salary);
    
        Journalist journalist = new Journalist();
        
        journalist.setUserName(uName);
        journalist.setPassword(new BCryptPasswordEncoder().encode(password));
        journalist.setEmail(email);
        journalist.setSalary(salary);
        journalist.setRol(Rol.JOURNALIST);
		
		journalist.setRegisterDate(new Date());
        
        
        journalistRepo.save(journalist);
        
    
    }
        
    @PreAuthorize("hasAnyRole('ROLE_JOURNALIST')")
	@Transactional
	public void addToList(String title) throws MyException{
		
		News news = newsRepo.searchByTitle(title);
		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        
                        
			String name = auth.getName();
		
                
		
		Journalist journalist = journalistRepo.findByName(name);
		
		
                System.out.println(news.getTitle());
                System.out.println(journalist.getUserName());
                
                if(news == null || journalist == null){
                
                
                    throw new MyException("the news or the user does not exist");
                
                }else{
                
                    ArrayList<News> list = new ArrayList<>();
                    list.add(news);
                    journalist.setList(list);
                
                }
		
	}
	
	
	
    
    public void validateUser(String uName, String password, String repeatPassword, String email, int salary)throws MyException{
        
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
            
            if(salary <= 0){
            
                throw new MyException("Salary cant be cero");
            
            }
                        
                      
            
        }
    
     @Override
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {
    
        Journalist newUser = journalistRepo.findByName(uName);
        
        if(newUser != null){
        
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + newUser.getRol().toString());
            
            permisos.add(p);
            
            User user = new User(newUser.getUserName(), newUser.getPassword(), permisos);
            
           
          return user;
        }else {
            return null;
                    
        }
       
        
        
    }
  
}
