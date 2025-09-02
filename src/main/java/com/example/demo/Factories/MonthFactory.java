package com.example.demo.Factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Month;
import com.example.demo.services.*;

@Service
public class MonthFactory {
    @Autowired
    private MonthService monthService;
    @Autowired
    private DayService dayService;

    

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
