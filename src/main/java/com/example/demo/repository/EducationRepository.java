package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Query(value = "SELECT COUNT(*) FROM tb_education WHERE cv_id = ?1", nativeQuery = true)
    public Long countByCVId(Integer cvId);

    @Query(value = "SELECT * FROM tb_education WHERE cv_id = ?", nativeQuery = true)
    public List<Education> getByCVId(@Param("id") Integer id);
}
