package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import com.example.demo.factories.MonthFactory;
import com.example.demo.models.*;
import com.example.demo.pogos.UserRegisterPogo;
import com.example.demo.services.*;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class LoginControllers {

    private final UserService userService;
    private final YearService yearService;
    private final MonthService monthService;
    private final MonthFactory monthFactory;

    public LoginControllers(UserService userService, YearService yearService, MonthService monthService, MonthFactory monthFactory){
        this.userService = userService;
        this.yearService = yearService;
        this.monthService = monthService;
        this.monthFactory = monthFactory;
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
    return "redirect:/register";
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


    @GetMapping("/years")
    public ModelAndView showYears(Map<String,Object> model, HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getUserByUsername(username);
        List<Year> years = user.getYears();
        if(years.isEmpty()){
            yearService.saveNewYear(2024, user);
        }
        model.put("years", years);
        model.put("username", username);
        return new ModelAndView("years",model);
    }

    @PostMapping("/years")
    public ModelAndView getYears(@RequestParam String number, HttpServletRequest request, Map<String,Object> model ){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Year yearToModel = new Year();
        User user =userService.getUserByUsername(username);
        List<Year> years = user.getYears();
        for (Year year : years) {
            if(year.getNumber() == Integer.parseInt(number)) yearToModel =year;
        }

        List <Month> months = yearToModel.getMonths();
        if (months.isEmpty()) {
            monthFactory.startNewMonth("Enero", 31, yearToModel);
        }

        model.put("months", months);

        return new ModelAndView("months",model);
    }
    


}
