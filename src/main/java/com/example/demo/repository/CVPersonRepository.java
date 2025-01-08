package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVPerson;

@Repository
public interface CVPersonRepository extends JpaRepository<CVPerson, Integer> {
}