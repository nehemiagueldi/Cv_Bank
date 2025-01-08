package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.WorkExp;

@Repository
public interface WorkExpRepository extends JpaRepository<WorkExp, Integer> {

}