package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer> {

}