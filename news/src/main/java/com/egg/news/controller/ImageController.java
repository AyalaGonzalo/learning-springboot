/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.controller;

import com.egg.news.entities.NewUser;
import com.egg.news.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ayala gonzalo
 */

/*
getCredentials
Object getCredentials()
The credentials that prove the principal is correct. This is usually a password, but could be anything relevant to the AuthenticationManager. Callers are expected to populate the credentials.
Returns:
the credentials that prove the identity of the Principal
getDetails
Object getDetails()
Stores additional details about the authentication request. These might be an IP address, certificate serial number etc.
Returns:
additional details about the authentication request, or null if not used
getPrincipal
Object getPrincipal()
The identity of the principal being authenticated. In the case of an authentication request with username and password, this would be the username. Callers are expected to populate the principal for an authentication request.
The AuthenticationManager implementation will often return an Authentication containing richer information as the principal for use by the application. Many of the authentication providers will create a UserDetails object as the principal.

Returns:
the Principal being authenticated or the authenticated principal after authentication.

*/
@Controller
@RequestMapping("/image")
public class ImageController{
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<byte[]>UserImage(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        
                        
		String name = auth.getName();
		
		NewUser user = userService.findByName(name);
		
		byte[] image = user.getImage().getContent();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		
		
		
		return new ResponseEntity<>(image,headers, HttpStatus.OK);
		
	}
        
        
	
}