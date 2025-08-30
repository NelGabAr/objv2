package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Month;

@Repository
public interface MonthRepo extends JpaRepository<Month, Long>{
    
    
}
