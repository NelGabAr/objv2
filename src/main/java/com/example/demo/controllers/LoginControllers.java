package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.example.demo.factories.YearFactory;
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
    private final YearFactory yearFactory;

    public LoginControllers(UserService userService, YearService yearService, MonthService monthService, MonthFactory monthFactory, YearFactory yearFactory){
        this.userService = userService;
        this.yearService = yearService;
        this.monthService = monthService;
        this.monthFactory = monthFactory;
        this.yearFactory = yearFactory;
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

        User user = userService.getUserByUsername(username);
        List<Year> years = user.getYears();
        if(years.isEmpty()){
            yearFactory.newYear(2024, user);
        }


        System.err.println("alguien se metio");
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
        
        model.put("year",yearToModel);
        model.put("months", yearToModel.getMonths());


        
        return new ModelAndView("months",model);
        
    }

    @GetMapping("/newYear")
    public String newYear(Map<String, Object> model, HttpServletRequest request){

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Year yearToModel = new Year();
        User user =userService.getUserByUsername(username);
        List<Year> years = user.getYears();
        
        int number = years.getLast().getNumber();
        
        yearFactory.newYear(number+1, user);

        
        







        return "redirect:/years";
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
        model.put("objective", month.getGoal());
        model.put("remainingDays",month.remainingDays());
        model.put("bdDaysToPass",month.bdDaysToPass());
        model.put("hdDaysToPass",month.hdDaysToPass());
        model.put("remainingSells", month.remainingSells());

        model.put("bdSellsNeeded", month.bdSellsNeeded());
        model.put("hdSellsNeeded", month.hdSellsNeeded());
        

        

        return new ModelAndView("table",model);
    }
    
@PostMapping("/table")
    public ModelAndView editTable(Map<String,Object> model, HttpServletRequest request,@RequestParam Long monthId, 
    @RequestParam List<String> totalSells, @RequestParam String objective,@RequestParam List<String> morningSells, @RequestParam List<String> afternoonSells, @RequestParam List<String> holiday){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.put("_csrf", csrfToken);
        Month month = monthService.getById(monthId);
        
        List<Double> LtotalSells = stringToDouble(totalSells);
        List<Double> LmorningSells = stringToDouble(morningSells);
        List<Double> LafternoonSells = stringToDouble(afternoonSells);
        Double goal = Double.parseDouble(objective);
/* 
        System.out.println(LtotalSells);
        System.out.println(LmorningSells);
        System.out.println(LafternoonSells);

*/
        monthService.uploadMonth(month, LtotalSells, LmorningSells, LafternoonSells, holiday, goal);
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
        model.put("remainingDays",month.remainingDays());
        model.put("month", month);

        model.put("bdDaysToPass",month.bdDaysToPass());
        model.put("hdDaysToPass",month.hdDaysToPass());

        model.put("remainingSells", month.remainingSells());

        model.put("bdSellsNeeded", month.bdSellsNeeded());
        model.put("hdSellsNeeded", month.hdSellsNeeded());
        



        return new ModelAndView("table",model);
    }
    
    public List<Double> stringToDouble(List<String> strings){
        List<Double> doubles = new ArrayList<>();
        for (String string : strings) {
            
            try {
                doubles.add(flatNumbers(string));
            } catch (Exception e) {
                doubles.add(0.0);
            }
            
        }

    return doubles;
    }

    @GetMapping("/graphs")
    public ModelAndView graphs(Map<String,Object> model, HttpServletRequest request){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user =userService.getUserByUsername(username);
        model.put("user", user);
        return new ModelAndView("graphs",model);
    }


    @GetMapping("/import")
    public String importHistoric(@RequestParam String content, @RequestParam Long yearID) {
        
        System.out.println(yearID);
        System.out.println(content);
        
        //TODO primero convertir todo en un array de numeros en strings

        String[] valores = content.replaceAll("[\\t\\n]+", " ").trim().split("\\s+");
        for (String string : valores) {
            System.out.println(string);
        }
        //segundo limpiar esos strings de formato para que sean todos 00.0 de ese estilo
        ArrayList<Double> cleanNumbers = new ArrayList<>();
        for (String string : valores) {
            //tercero pasarlo todo a doubles
            cleanNumbers.add(flatNumbers(string));

        }
        
        System.out.println(cleanNumbers.size());
        System.out.println(cleanNumbers.toString());
        //cuarto establecer divisiones en distintos arrays de 31, 30, 29 o 28 dias
        //quinto, rellenar los meses del año con esos arrays



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        List<Year> years = user.getYears();
        Year currentYear = null;
        for (Year year : years) {
            if(year.getId()==yearID){
                currentYear=year;
            }
        }
        //tomar los meses

            List<Month> months = currentYear.getMonths();
        int ext = 0;
        for (int i= 0; i<31;i++){
            System.err.println("va por el dia : " +i);
            for(int j = 0; j<12; j++){
                System.err.println("falla en el mes: " +j);
                if(i>=months.get(j).getDays().size()){
                    
                    System.err.println("mu grande");
                }
                else{
                        if (j==0&&i==5) {
                            months.get(j).getDays().get(i).setTotalSells(0.0);
                        }
                        else if(j==11&&i==24){
                            months.get(j).getDays().get(i).setTotalSells(0.0);
                        
                        }
                        else{
                            months.get(j).getDays().get(i).setTotalSells(cleanNumbers.get(ext));
                            ext++;
                        }
                }
               }

        }
        for (Month aux : months) {
            monthService.update(aux);
        }



        




//buscamos el año



        return "redirect:/years";

    }
    
    public Double flatNumbers(String wildDouble){

        wildDouble = wildDouble.trim();

        // Detectar el último separador decimal (coma o punto)
        int ultimaComa = wildDouble.lastIndexOf(',');
        int ultimoPunto = wildDouble.lastIndexOf('.');

        int separadorDecimal = Math.max(ultimaComa, ultimoPunto);

        if (separadorDecimal == -1) {
            // No hay separador decimal, asumimos número entero
            return Double.parseDouble(wildDouble);
        }

        // Separar parte entera y decimal
        String parteEntera = wildDouble.substring(0, separadorDecimal);
        String parteDecimal = wildDouble.substring(separadorDecimal + 1);

        // Eliminar separadores de miles en la parte entera
        parteEntera = parteEntera.replaceAll("[.,]", "");

        // Unir con punto como separador decimal
        wildDouble= parteEntera + "." + parteDecimal;
        return Double.parseDouble(wildDouble);

        
    }
    
}
