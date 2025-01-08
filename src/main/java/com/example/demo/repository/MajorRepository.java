package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

}