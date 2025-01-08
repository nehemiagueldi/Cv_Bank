package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}