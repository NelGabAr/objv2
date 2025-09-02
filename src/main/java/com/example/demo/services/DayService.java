package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.models.Day;
import com.example.demo.models.Month;
import com.example.demo.repositories.DayRepo;

@Service
public class DayService {
    
    private final DayRepo dayRepo;



    public DayService(DayRepo dayRepo){
        this.dayRepo = dayRepo;
    }



    public Day saveDay(Month month){
        Day day = new Day();
        day.setMonth(month);
        return dayRepo.save(day);
    }

}
