package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Query(value = "SELECT COUNT(*) FROM tb_education WHERE cv_id = ?1", nativeQuery = true)
    public Long countByCVId(Integer cvId);
}
