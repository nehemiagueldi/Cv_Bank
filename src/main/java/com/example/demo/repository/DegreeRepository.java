package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Integer> {

}