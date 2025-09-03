package com.example.demo.controllers;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.services.DayService;
import com.example.demo.services.UserService;

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
    


}
