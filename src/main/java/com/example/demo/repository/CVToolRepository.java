package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVTool;

@Repository
public interface CVToolRepository extends JpaRepository<CVTool, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_cv_tool WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);
}