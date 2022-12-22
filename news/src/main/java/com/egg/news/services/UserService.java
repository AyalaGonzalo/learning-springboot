/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.services;

import com.egg.news.entities.Image;
import com.egg.news.entities.NewUser;
import com.egg.news.myexception.MyException;
import com.egg.news.newsenums.Rol;
import com.egg.news.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ayala gonzalo
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void createUser(MultipartFile imageFile, String uName, String password, String repeatPassword, String email) throws MyException, IOException {

        validateUser(uName, password, repeatPassword, email);

        NewUser user = new NewUser();

        user.setUserName(uName);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        user.setRol(Rol.USER);

        user.setEmail(email);

        user.setRegisterDate(new Date());

        Image image = imageService.uploadImage(imageFile);

        user.setImage(image);

        userRepository.save(user);

    }
    
    

    public void updateUser(MultipartFile imageFile, String id, String uName, String email, String password, String repeatPassword) throws MyException, IOException {

        validateUser(uName, password, repeatPassword, email);

        Optional<NewUser> answer = userRepository.findById(id);

        if (answer.isPresent()) {

            NewUser user = answer.get();

            user.setUserName(uName);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setEmail(email);
            user.setRol(Rol.USER);

            String idImage = null;

            if (user.getImage() != null) {

                idImage = user.getImage().getId();

            }

            Image image = imageService.update(imageFile, idImage);

            user.setImage(image);

            userRepository.save(user);

        }

    }

    public void validateUser(String uName, String password, String repeatPassword, String email) throws MyException {

        if (uName.isEmpty() || uName.isBlank()) {

            throw new MyException("User name can't be empty");

        }

        if (password.isEmpty() || password.isBlank() || password.length() <= 5) {

            throw new MyException("Please enter a password and must be more than 5 characters");
        }

        if (repeatPassword.isEmpty() || repeatPassword.isBlank()) {

            throw new MyException("Please repeat password");

        }

        if (!repeatPassword.equals(password)) {

            throw new MyException("You must repeat the password correctly!");

        }

        if (email.isEmpty() || email.isBlank()) {

            throw new MyException("email can't be empty");

        }

        if (!email.contains("@") && !email.contains(".com")) {

            throw new MyException("email not allowed");

        }

    }

    @Override
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {

        NewUser newUser = userRepository.findByUName(uName);

        if (newUser != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + newUser.getRol().toString());

            permisos.add(p);

            User user = new User(newUser.getUserName(), newUser.getPassword(), permisos);

            return user;
        } else {
            return null;

        }

    }
	
	@Transactional(readOnly = true)
    public NewUser getOne(String id) {

        return userRepository.getOne(id);

    }
    
    @Transactional
    public NewUser findByName(String uName){
    
    
        return userRepository.findByUName(uName);
        
    }

}
