package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  @Query(value = "SELECT COUNT(*) FROM tb_project WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);

  @Query(value = "SELECT * FROM tb_project WHERE cv_id = ?", nativeQuery = true)
  public List<Project> getByCVId(@Param("id") Integer id);
}