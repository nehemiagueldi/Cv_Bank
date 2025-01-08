package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_training WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);
}