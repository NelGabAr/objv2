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

    public Day save(Day day){
        return dayRepo.save(day);
    }

    public Day saveNewDay(Month month, int dayNumber){
        Day day = new Day();
        day.setDayNumber(dayNumber);
        day.setMonth(month);
        return dayRepo.save(day);
    }

}
