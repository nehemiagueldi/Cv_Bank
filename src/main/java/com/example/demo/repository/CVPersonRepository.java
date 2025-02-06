package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVPerson;

@Repository
public interface CVPersonRepository extends JpaRepository<CVPerson, Integer> {
        @Query(value = "SELECT * FROM tb_cv_person WHERE random_string = ?", nativeQuery = true)
        public CVPerson getCvByRandomString(@Param("randomString") String randomString);

        @Query(value = "SELECT * FROM tb_cv_person cp " +
                        "JOIN tb_person p ON cp.person_id = p.id " +
                        "WHERE LOWER(position) LIKE LOWER(CONCAT('%', :search, '%')) ", nativeQuery = true)
        public List<CVPerson> getCvByPosition(@Param("search") String search);

        @Query(value = "SELECT * FROM tb_cv_person cp " +
                        "JOIN tb_person p ON cp.person_id = p.id " +
                        "WHERE LOWER(CASE " +
                        "            WHEN p.gender = 'M' THEN 'Male' " +
                        "            WHEN p.gender = 'F' THEN 'Female' " +
                        "            ELSE NULL " +
                        "         END) LIKE LOWER(CONCAT('%', :gender, '%')) ", nativeQuery = true)
        public List<CVPerson> getCvByGender(@Param("gender") String gender);

        @Query(value = "SELECT * FROM tb_cv_person cp " +
                        "JOIN tb_person p ON cp.person_id = p.id " +
                        "WHERE TIMESTAMPDIFF(YEAR, p.birthdate, CURRENT_DATE) BETWEEN :age AND :ageEnd", nativeQuery = true)
        public List<CVPerson> getCvByAge(@Param("age") Integer age, Integer ageEnd);

        @Query(value = "SELECT cp.percentage_progress FROM tb_cv_person cp JOIN tb_person p ON p.id = cp.person_id WHERE p.email = ?", nativeQuery = true)
        public Double getPercentagerProgress(@Param("email") String email);

        @Query(value = "SELECT COUNT(*) FROM tb_cv_person cp " +
                        "JOIN tb_person p ON cp.person_id = p.id " +
                        "WHERE LOWER(position) LIKE LOWER(CONCAT('%', :search, '%')) " +
                        "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
                        "OR (TIMESTAMPDIFF(YEAR, p.birthdate, CURRENT_DATE)) <= CAST(:search AS SIGNED) " +
                        "OR LOWER(CASE " +
                        "            WHEN p.gender = 'M' THEN 'Male' " +
                        "            WHEN p.gender = 'F' THEN 'Female' " +
                        "            ELSE NULL " +
                        "         END) LIKE LOWER(CONCAT('%', :search, '%')) ", nativeQuery = true)
        long countFilteredCVPerson(@Param("search") String search);
}
