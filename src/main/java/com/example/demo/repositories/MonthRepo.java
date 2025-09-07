package com.example.demo.repositories;
import java.time.Year;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Month;
import java.util.List;
import java.util.Optional;


@Repository
public interface MonthRepo extends JpaRepository<Month, Long>{
    
    
}
