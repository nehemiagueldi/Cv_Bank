package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVPerson;

@Repository
public interface CVPersonRepository extends JpaRepository<CVPerson, Integer> {
    @Query(value = "SELECT COUNT(*) FROM tb_cv_person", nativeQuery=true)
    public long countPerson();
}