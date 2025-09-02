package com.example.demo.Factories;

import com.example.demo.models.*;
import com.example.demo.services.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthFactory {
    
    private final MonthService monthService;
    private final DayService dayService;

    public MonthFactory(MonthService monthService, DayService dayService){
        this.monthService = monthService;
        this.dayService = dayService;
    }



    @Transactional
    public Month startNewMonth(String name, int totalDays){

        Month month = new Month();
        month.setName(name);
        month = monthService.save(month);
        
        for(int i=0;i<totalDays;i++){
            dayService.saveDay(month);
        }
        return monthService.save(month);
    }



}
