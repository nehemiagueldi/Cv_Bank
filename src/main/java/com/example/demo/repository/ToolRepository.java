package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {

}