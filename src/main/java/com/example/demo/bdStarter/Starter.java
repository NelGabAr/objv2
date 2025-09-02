package com.example.demo.bdStarter;

import com.example.demo.Factories.MonthFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

    @Component
    public class Starter {
        @Autowired
        MonthFactory monthFactory;
    
        @PostConstruct
        public void start(){

        monthFactory.startNewMonth("febrero", 28);
    }


}
