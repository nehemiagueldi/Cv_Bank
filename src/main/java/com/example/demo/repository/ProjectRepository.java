package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_project WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);
}