package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

}