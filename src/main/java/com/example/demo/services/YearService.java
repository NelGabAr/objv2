package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.models.Year;
import com.example.demo.repositories.YearRepo;

@Service
public class YearService {
    
    private final YearRepo yearRepo;

    public YearService(YearRepo yearRepo){
        this.yearRepo = yearRepo;
    }

    public Year save(Year year){
        return yearRepo.save(year);
    }

    public Year saveNewYear(int number, User user){
        Year year = new Year();
        year.setUser(user);
        year.setNumber(number);
        return yearRepo.save(year);
        
    }

    
}
