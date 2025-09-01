package com.example.demo.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Day;
import com.example.demo.models.Month;
import com.example.demo.repositories.MonthRepo;




@Service
public class MonthService {
    @Autowired
    MonthRepo monthRepo;
    
   public Month save(Month month){
    return monthRepo.save(month);
   }
}
