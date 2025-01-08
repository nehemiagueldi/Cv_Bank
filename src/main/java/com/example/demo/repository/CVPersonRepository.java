package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CVPerson;

public interface CVPersonRepository extends JpaRepository<CVPerson, Integer> {
    // @Query("SELECT ")
}