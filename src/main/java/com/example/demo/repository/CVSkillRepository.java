package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVSkill;

@Repository
public interface CVSkillRepository extends JpaRepository<CVSkill, Integer> {

}