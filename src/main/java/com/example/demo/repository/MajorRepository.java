package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {
    @Query(value = "SELECT * FROM tb_major WHERE faculty_id = :faculty", nativeQuery = true)
    public List<Major> getMajorByFacutlyId(@Param("faculty") Integer faculty);
}