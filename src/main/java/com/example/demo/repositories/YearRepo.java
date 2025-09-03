package com.example.demo.repositories;

import com.example.demo.models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearRepo extends JpaRepository<Year,Long> {
    
}
