package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Integer> {

}