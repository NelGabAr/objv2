package com.example.demo.repositories;

import com.example.demo.models.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearRepo extends JpaRepository<Year,Long> {

    public List<Year> findByUser(User user);
}
