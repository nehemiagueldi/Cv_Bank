package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_training WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);

  @Query(value = "SELECT * FROM tb_training WHERE cv_id = ?", nativeQuery = true)
  public List<Training> getByCVId(@Param("id") Integer id);
}