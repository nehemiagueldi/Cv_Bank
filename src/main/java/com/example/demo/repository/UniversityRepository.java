package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {

}