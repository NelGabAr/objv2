package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import com.example.demo.factories.MonthFactory;
import com.example.demo.models.*;
import com.example.demo.pogos.DayPogo;
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
                years = user.getYears();

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
        

        model.put("months", yearToModel.getMonths());

        return new ModelAndView("months",model);
    }

    @GetMapping("/table")
    public ModelAndView showTable(Map<String,Object> model, HttpServletRequest request,@RequestParam Long monthId){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        Month month = monthService.getById(monthId);

        model.put("month", month);
        model.put("totalsells", month.totalSells());
        model.put("totalmorningsells",month.totalMorningSells());
        model.put("totalafternoonsells",month.totalAfternoonSells());
        model.put("averagebdsells",month.averageBDsells());
        model.put("averagehdsells",month.averageHDsells());
        model.put("averagesells",month.averageSells());
        model.put("averagemorningsells",month.averageMorningSells());
        model.put("averageafternoonsells",month.averageAfternoonSells());
        model.put("month", month);

        return new ModelAndView("table",model);
    }
    
@PostMapping("/table")
    public ModelAndView editTable(Map<String,Object> model, HttpServletRequest request,@RequestParam Long monthId, 
    @RequestParam List<String> totalSells, @RequestParam List<String> morningSells, @RequestParam List<String> afternoonSells, @RequestParam List<String> holiday){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        Month month = monthService.getById(monthId);
        
        List<Double> LtotalSells = stringToDouble(totalSells);
        List<Double> LmorningSells = stringToDouble(morningSells);
        List<Double> LafternoonSells = stringToDouble(afternoonSells);

        System.out.println(LtotalSells);
        System.out.println(LmorningSells);
        System.out.println(LafternoonSells);


        monthService.uploadMonth(month, LtotalSells, LmorningSells, LafternoonSells, holiday);
        month = monthService.getById(monthId);

        


        model.put("month", month);
        model.put("totalsells", month.totalSells());
        model.put("totalmorningsells",month.totalMorningSells());
        model.put("totalafternoonsells",month.totalAfternoonSells());
        model.put("averagebdsells",month.averageBDsells());
        model.put("averagehdsells",month.averageHDsells());
        model.put("averagesells",month.averageSells());
        model.put("averagemorningsells",month.averageMorningSells());
        model.put("averageafternoonsells",month.averageAfternoonSells());
        model.put("month", month);

        



        return new ModelAndView("table",model);
    }
    
    public List<Double> stringToDouble(List<String> strings){
        List<Double> doubles = new ArrayList<>();
        for (String string : strings) {
            doubles.add(Double.parseDouble(string));
        }

    return doubles;
    }

    
}
