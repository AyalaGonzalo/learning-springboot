/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.controller;

/**
 *
 * @author Ayala gonzalo
 */
import com.egg.news.myexception.MyException;
import com.egg.news.entities.News;
import com.egg.news.services.AdminService;
import com.egg.news.services.JournalistService;
import com.egg.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author Ayala gonzalo
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private JournalistService journalistService;

    
     @GetMapping("/tools")
    public String adminPage(ModelMap model) {

        List<News> list = newsService.listAllNews();

        model.addAttribute("news", list);

        return "admin";

    }
    
    
    
    @GetMapping("/tools/create-news")
    public String createNews() {

        return "news_form";

    }
    
   

    @PostMapping("/tools/create-news")
    public String postNews(@RequestParam(required = false) String isbn, @RequestParam String title, ModelMap model, 
            RedirectAttributes redirect) {

        try {

            newsService.buildBook(title);

            journalistService.addToList(title);
            
            List<News> list = newsService.listAllNews();

            model.addAttribute("news", list);

            redirect.addFlashAttribute("success", "Thew news was successful created");

            return "redirect:/admin/tools";

        } catch (MyException e) {

            redirect.addFlashAttribute("error", e.getMessage());

            return "redirect:/admin/tools/create-news";

        }

    }
    
     @GetMapping("/tools/create-news-admin")
    public String createNewsByAdmin(){
    
        return "create_news_admin";
    
    }
    
    @PostMapping("/tools/create-news-admin")
    public String newsCreatedByAdmin(@RequestParam String title, ModelMap model, RedirectAttributes redirect){
    
        try{
        
            newsService.createBookByAdmin(title);
            
            model.addAttribute("success", "The news was successful created");
            
            return "redirect:/admin/tools";
        
        
        }catch(MyException e){
        
           
            redirect.addFlashAttribute("error", e.getMessage());
            
            return "redirect:/admin/tools/create-news-admin";
        
        
        }
    
        
    }

   

    @GetMapping("/tools/modify-news/{isbn}")
    public String modifyNews(@PathVariable("isbn") String isbn, ModelMap model) {

        model.addAttribute("news", newsService.getOne(isbn));

        return "modify_news";

    }

    @PostMapping("/tools/modify-news/{isbn}")
    public String changedNews(@PathVariable("isbn") String isbn, String title, ModelMap model, RedirectAttributes redirect) {

        try {

            newsService.modifyNews(isbn, title);

            List<News> list = newsService.listAllNews();

            model.addAttribute("news", list);

            redirect.addFlashAttribute("success", "The news was successful modified");

            return "redirect:/admin/tools";

        } catch (MyException e) {

            redirect.addFlashAttribute("error", "The title already exist");

            return "redirect:/admin/tools/modify-news/{isbn}";

        }

    }

    @GetMapping("/deleted/{isbn}")
    public String deleted(@PathVariable("isbn") String isbn, ModelMap model) {

        try {

            newsService.deleteNews(isbn);

            List<News> list = newsService.listAllNews();

            model.addAttribute("news", list);

            return "admin";

        } catch (MyException e) {

            System.out.println(e);

            return "admin";

        }

    }

    @GetMapping("tools/admin-up")
    public String adminUp() {

        return "admin_up";
    }

    @PostMapping("tools/admin-up-done")
    public String adminUpDone(@RequestParam String uName, @RequestParam String email, @RequestParam String password,
            @RequestParam String repeatPassword, ModelMap model, RedirectAttributes redirect) throws MyException {

        try {
            adminService.createAdminUser(uName, password, repeatPassword, email);
            List<News> list = newsService.listAllNews();

            model.addAttribute("news", list);

            redirect.addFlashAttribute("success", "A new admin was created");

            return "redirect:/admin/tools";
			
        } catch (MyException ex) {

             redirect.addFlashAttribute("error", ex);

            return "admin_up";

        }
    }
    
    
    @GetMapping("/tools/add-journalist")
    public String addJournalist(){
    
        return "journalist_form";
    
    }
    
    @PostMapping("/tools/add-journalist")
    public String journalistAdded(@RequestParam String uName, @RequestParam String password
            , @RequestParam String repeatPassword,@RequestParam String email, @RequestParam int salary,
            RedirectAttributes redirect){
    
        try{
            
              journalistService.createJournalist(uName, password, repeatPassword, email, salary);
              
              redirect.addFlashAttribute("success", "The journalist was successfully added");
              
               return "redirect:/admin/tools";
            
        }catch(MyException ex){
                
            
            redirect.addFlashAttribute("error", ex);
            
            return "journalist_form";
            
        }
      
        
    
       
    
    }

}
