/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.controller;

import com.egg.news.entities.Journalist;
import com.egg.news.entities.News;
import com.egg.news.myexception.MyException;
import com.egg.news.repository.JournalistRepository;
import com.egg.news.services.NewsService;
import com.egg.news.services.UserService;
import com.egg.news.services.AdminService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 *
 * @author Ayala gonzalo
 */
@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JournalistRepository journalistRepo;

    @GetMapping("/")
    public String index(ModelMap model) {

        List<News> list = newsService.listAllNews();
        model.addAttribute("news", list);
        return "index";

    }

    @GetMapping("/section-news")
    public String section() {

        return "news_section";
    }

    @GetMapping("/sign-up")
    public String signUp() {

        return "sign_up";

    }

    @PostMapping("/sign-up-done")
    public String signUpDone(@RequestParam String uName, @RequestParam String email, @RequestParam String password,
            @RequestParam String repeatPassword, MultipartFile imageFile, ModelMap model, RedirectAttributes redirect) throws IOException {

        try {

            userService.createUser(imageFile, uName, password, repeatPassword, email);

            //List<News> list = newsService.listAllNews();

            //model.addAttribute("news", list);
			
			redirect.addFlashAttribute("success", "Your account was successfully created");

            return "redirect:/";

        } catch (MyException ex) {

            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/sign-up";

        }

    }

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false) String error, ModelMap model) {

        if (error != null) {

            model.put("error", "User name or password incorrect");

        }

        return "sign_in";

    }
    
    /*
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
        String username = ((UserDetails)principal).getUsername();
    } else {
        String username = principal.toString();
    }
    
    
    */

	@GetMapping("/index-2")
    public String indexLog(ModelMap model) {
		
		
		return "index_2";
		
	}
	/*
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_JOURNALIST')")
    @GetMapping("/index-2")
    public String indexLog(ModelMap model, HttpServletRequest logged) {

        if (logged.isUserInRole("ROLE_USER")) {

            List<News> list = newsService.listAllNews();

            model.addAttribute("news", list);

            return "index_2";

        } else if (logged.isUserInRole("ROLE_ADMIN")) {

            return "redirect:/admin/tools";

        } else if (logged.isUserInRole("ROLE_JOURNALIST")) {

            
            
             String username;
            
            if(principal instanceof UserDetails){
            
                username = ((UserDetails)principal).getUsername();
            
            }else{
            
                username = principal.toString();
            
            }
            
            System.out.println(username);
            
            return "redirect:/admin/tools"

        }

        return "sign_in";
    }
	*/

}
