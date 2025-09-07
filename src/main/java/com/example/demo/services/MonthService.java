package com.example.demo.services;

import com.example.demo.models.Month;
import com.example.demo.models.Year;
import com.example.demo.repositories.MonthRepo;

import java.util.Optional;

import org.springframework.stereotype.*;



@Service
public class MonthService {

    private final MonthRepo monthRepo;

    public MonthService(MonthRepo monthRepo){
        this.monthRepo = monthRepo;
    }

    public Month save(Month month){
    return monthRepo.save(month);
    }
    
    public Month saveNewMonth(Year year){
        Month month = new Month();
        month.setYear(year);
        return monthRepo.save(month);
    }

    public Month getById(Long id){
        Month month = monthRepo.findById(id)
                                .orElseThrow(()-> new RuntimeException("mes no encontrado"));
                                
        return month;
    }
}