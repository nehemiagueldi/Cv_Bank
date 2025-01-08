package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Major;

public interface MajorRepository extends JpaRepository<Major, Integer> {

}