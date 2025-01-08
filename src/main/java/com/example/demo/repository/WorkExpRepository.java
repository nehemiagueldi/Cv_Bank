package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.WorkExp;

public interface WorkExpRepository extends JpaRepository<WorkExp, Integer> {
    
}