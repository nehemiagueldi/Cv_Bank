package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVSkill;

@Repository
public interface CVSkillRepository extends JpaRepository<CVSkill, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_cv_skill WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);
}