package com.example.demo.factories;


import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.models.Year;
import com.example.demo.repositories.DayRepo;
import com.example.demo.repositories.YearRepo;
import com.example.demo.services.YearService;

@Service
public class YearFactory {
    
    
    private final YearService yearService;
    private final MonthFactory monthFactory;
    

    public YearFactory(MonthFactory monthFactory, YearService yearService){
        this.monthFactory = monthFactory;
        this.yearService = yearService;

    }



    public Year newYear(int number, User user){
        Year year = yearService.saveNewYear(number,user);
        monthFactory.startNewMonth("enero", 31, year);

        if(number%4==0 && number%100!=0){
        monthFactory.startNewMonth("febrero", 29, year);
        }
        else{
        monthFactory.startNewMonth("febrero", 28, year);
            
        }
        
        monthFactory.startNewMonth("marzo", 31, year);
        monthFactory.startNewMonth("abril", 30, year);
        monthFactory.startNewMonth("mayo", 31, year);
        monthFactory.startNewMonth("junio", 30, year);
        monthFactory.startNewMonth("julio", 31, year);
        monthFactory.startNewMonth("agosto", 31, year);
        monthFactory.startNewMonth("septiembre", 30, year);
        monthFactory.startNewMonth("octubre", 31, year);
        monthFactory.startNewMonth("noviembre", 30, year);
        monthFactory.startNewMonth("diciembre", 31, year);        



        return yearService.save(year);
    }



}
