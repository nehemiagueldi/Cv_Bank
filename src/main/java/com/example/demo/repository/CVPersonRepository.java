package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVPerson;

@Repository
public interface CVPersonRepository extends JpaRepository<CVPerson, Integer> {
    @Query(value = "SELECT * FROM tb_cv_person WHERE random_string = ?", nativeQuery = true)
    public CVPerson getCvByRandomString(@Param("randomString") String randomString);

    @Query(value = "SELECT cp.percentage_progress FROM tb_cv_person cp JOIN tb_person p ON p.id = cp.person_id WHERE p.email = ?", nativeQuery = true)
    public Double getPercentagerProgress(@Param("email") String email);
}