package com.example.demo.bdStarter;

import com.example.demo.factories.MonthFactory;
import com.example.demo.models.*;
import com.example.demo.services.*;


import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;


@SuppressWarnings("unused")
@Component
public class Starter {
        
    private final MonthFactory monthFactory;
    private final YearService yearService;
    private final UserService userService;

    public Starter(MonthFactory monthFactory, YearService yearService, UserService userService){
    this.monthFactory = monthFactory;
    this.yearService = yearService;
    this.userService = userService;
    }



    @PostConstruct
    public void start(){
/*
    User user = userService.newUser("paco","pacopass");
    Year year = yearService.saveNewYear(2025,user);
    
    monthFactory.startNewMonth("enero", 31, year);
*/
    }



}
