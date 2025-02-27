package com.example.demo.repository;

import java.util.List;

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

        @Query(value = "SELECT COUNT(*) FROM tb_cv_person cp " +
                        "JOIN tb_person pe ON cp.person_id = p.id " +
                        "JOIN tb_projects pr ON cp.id = pr.cv_id " +
                        "JOIN tb_education e ON cp.id = e.cv_id " +
                        "JOIN tb_work_exp w ON cp.id = w.cv_id " +
                        "JOIN tb_training t ON cp.id = t.cv_id " +
                        "JOIN tb_cv_tool ct ON cp.id = ct.cv_id " +
                        "JOIN tb_cv_skill cs ON cp.id = cs.cv_id " +
                        "JOIN tb_skill s ON s.id = cs.skill_id " +
                        "JOIN tb_major s ON m.id = e.major_id " +
                        "JOIN tb_university u ON u.id = e.university_id " +
                        "WHERE (" +
                        "   (:search IS NULL OR (" +
                        "       LOWER(cp.position) LIKE LOWER(CONCAT('%', :search, '%')) " +
                        "       OR LOWER(w.company) LIKE LOWER(CONCAT('%', :search, '%'))" +
                        "   )) " +
                        ") " +
                        "AND (" +
                        "   (:gender IS NULL OR " +
                        "   LOWER(pe.gender) LIKE LOWER(CONCAT('%', :gender, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:position IS NULL OR " +
                        "   LOWER(cp.position) LIKE LOWER(CONCAT('%', :position, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:age IS NULL OR :ageEnd IS NULL OR " +
                        "   TIMESTAMPDIFF(YEAR, pe.birthdate, CURRENT_DATE) BETWEEN :age AND :ageEnd) " +
                        ") " +
                        "AND ( " +
                        " (COALESCE(:skill) IS NULL OR " +
                        " s.name IN (:skill)) " +
                        ") " +
                        "AND (" +
                        "   (:gpa IS NULL OR :gpaEnd IS NULL OR e IS NULL OR " +
                        " e.gpa > :gpa AND e.gpa <= :gpaEnd) " +
                        ") " +
                        "AND (" +
                        "   (:major IS NULL OR " +
                        "   e IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :major, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:university IS NULL OR " +
                        "   e IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :university, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:company IS NULL AND :jobDesc IS NULL) OR " +
                        "   EXISTS (SELECT 1 FROM WorkExp w WHERE w.cvPerson = cp " +
                        "           AND (:company IS NULL OR LOWER(w.company) LIKE LOWER(CONCAT('%', :company, '%'))) "
                        +
                        "           AND (:jobDesc IS NULL OR LOWER(w.description) LIKE LOWER(CONCAT('%', :jobDesc, '%')))) "
                        +
                        ")", nativeQuery = true)
        long countFilteredCVPerson(
                        @Param("search") String search,
                        @Param("position") String position,
                        @Param("gender") String gender,
                        @Param("age") Integer age,
                        @Param("ageEnd") Integer ageEnd,
                        @Param("skill") List<String> skill,
                        @Param("gpa") Double gpa,
                        @Param("gpaEnd") Double gpaEnd,
                        @Param("major") String major,
                        @Param("university") String university,
                        @Param("company") String company,
                        @Param("jobDesc") String jobDesc);

        @Query("SELECT DISTINCT cp FROM CVPerson cp " +
                        "LEFT JOIN cp.person pe " +
                        "LEFT JOIN cp.projects pr " +
                        "LEFT JOIN cp.educations e " +
                        "LEFT JOIN cp.workExps w " +
                        "LEFT JOIN cp.trainings t " +
                        "LEFT JOIN cp.cvTools ct " +
                        "LEFT JOIN cp.cvSkills cs " +
                        "LEFT JOIN cs.skill s " +
                        "LEFT JOIN e.major m " +
                        "LEFT JOIN e.university u " +
                        "WHERE (" +
                        "   (:search IS NULL OR (" +
                        "       LOWER(cp.position) LIKE LOWER(CONCAT('%', :search, '%')) " +
                        "       OR LOWER(w.company) LIKE LOWER(CONCAT('%', :search, '%'))" +
                        "   )) " +
                        ") " +
                        "AND (" +
                        "   (:gender IS NULL OR " +
                        "   LOWER(pe.gender) LIKE LOWER(CONCAT('%', :gender, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:position IS NULL OR " +
                        "   LOWER(cp.position) LIKE LOWER(CONCAT('%', :position, '%'))) " +
                        ") " +
                        "AND (" +
                        "   (:age IS NULL OR :ageEnd IS NULL OR " +
                        "   TIMESTAMPDIFF(YEAR, pe.birthdate, CURRENT_DATE) BETWEEN :age AND :ageEnd) " +
                        ") " +
                        "AND ( " +
                        " (COALESCE(:skill) IS NULL OR " +
                        " s.name IN (:skill)) " +
                        ") " +
                        "AND (" +
                        "    (:gpa IS NULL AND :gpaEnd IS NULL) " +
                        "    OR EXISTS ( " +
                        "        SELECT 1 FROM Education e " +
                        "        WHERE e.cvPerson = cp " +
                        "        AND e.gpa > :gpa " +
                        "        AND e.gpa <= :gpaEnd " +
                        "    ) " +
                        ") "
                        +
                        "AND (" +
                        "   (:major IS NULL AND :university IS NULL) " + // Jika kedua parameter NULL, tidak ada filter
                                                                         // (semua data muncul)
                        "   OR EXISTS (SELECT 1 FROM Education e WHERE e.cvPerson = cp " +
                        "              AND (:major IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :major, '%'))) " +
                        "              AND (:university IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :university, '%')))) "
                        +
                        ") "
                        +
                        "AND (" +
                        "   (:company IS NULL AND :jobDesc IS NULL) OR " +
                        "   EXISTS (SELECT 1 FROM WorkExp w WHERE w.cvPerson = cp " +
                        "           AND (:company IS NULL OR LOWER(w.company) LIKE LOWER(CONCAT('%', :company, '%'))) "
                        +
                        "           AND (:jobDesc IS NULL OR LOWER(w.description) LIKE LOWER(CONCAT('%', :jobDesc, '%')))) "
                        +
                        ")")
        List<CVPerson> findCvPersonDTOs2(
                        @Param("search") String search,
                        @Param("position") String position,
                        @Param("gender") String gender,
                        @Param("age") Integer age,
                        @Param("ageEnd") Integer ageEnd,
                        @Param("skill") List<String> skill,
                        @Param("gpa") Double gpa,
                        @Param("gpaEnd") Double gpaEnd,
                        @Param("major") String major,
                        @Param("university") String university,
                        @Param("company") String company,
                        @Param("jobDesc") String jobDesc);

}