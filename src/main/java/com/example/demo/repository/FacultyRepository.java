package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

}