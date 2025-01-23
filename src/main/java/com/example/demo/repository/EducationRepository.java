package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Education;
import com.example.demo.model.dto.EduDTO;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query(value = "SELECT COUNT(*) FROM tb_education WHERE cv_id = ?1", nativeQuery = true)
    public Long countByCVId(Integer cvId);

    @Query(value = "SELECT * FROM tb_education WHERE cv_id = ?", nativeQuery = true)
    public List<Education> getByCVId(@Param("id") Integer id);

    @Query("SELECT new com.example.demo.model.dto.EduDTO(e.gpa, u.name, d.name, m.name, e.startDate, e.endDate) FROM Education e JOIN e.university u JOIN e.degree d JOIN e.major m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<EduDTO> findMajor(@Param("search") String search);

    @Query(value = "SELECT * FROM tb_education e JOIN tb_university u ON e.university_id = u.id JOIN tb_degree d ON e.degree_id = d.id JOIN tb_major m ON e.major_id = m.id WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Education> findMajorBySearch(@Param("search") String search);
}
