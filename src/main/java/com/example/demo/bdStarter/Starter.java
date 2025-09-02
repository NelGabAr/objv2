package com.example.demo.bdStarter;

import com.example.demo.Factories.MonthFactory;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class Starter {
        
    private final MonthFactory monthFactory;
    
    public Starter(MonthFactory monthFactory){
    this.monthFactory = monthFactory;
    }



    @PostConstruct
    public void start(){

    monthFactory.startNewMonth("enero", 31);
    
    }



}
