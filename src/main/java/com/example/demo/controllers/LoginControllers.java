package com.example.demo.controllers;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.User;
import com.example.demo.pogos.UserRegisterPogo;
import com.example.demo.services.DayService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/")
public class LoginControllers {

    private final UserService userService;
    public LoginControllers(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/")
    public ModelAndView startPage(Map<String,Object> model){
        return new ModelAndView("helloPage",model);
    }


    @GetMapping("/register")
    public ModelAndView registerPage(Map<String,Object> model, HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        return new ModelAndView("registerPage",model);
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegisterPogo userRegisterPogo) {

        if(userRegisterPogo.getPassword().equals(userRegisterPogo.getRepassword())){

            userService.newUser(userRegisterPogo.getUsername(), userRegisterPogo.getPassword());
        
            return "redirect:/";
        }

        else{
            return "redirect:/register";
        }
        

        
    }
    



    
    @GetMapping("/login")
    public ModelAndView login(Map<String,Object> model, HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);

        return new ModelAndView("loginPage",model);
    }
    
    @GetMapping("/home")
    public ModelAndView home(Map<String,Object> model, HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        model.put("username", username);
        return new ModelAndView("home",model);
    }
    



}
