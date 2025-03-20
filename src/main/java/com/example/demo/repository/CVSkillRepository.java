package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVSkill;

@Repository
public interface CVSkillRepository extends JpaRepository<CVSkill, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_cv_skill WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);

  @Query(value = "SELECT * FROM tb_cv_skill WHERE cv_id = ?", nativeQuery = true)
  public List<CVSkill> getByCVId(@Param("id") Integer id);

  @Query(value = "SELECT * FROM tb_cv_skill cs JOIN tb_skill s ON cs.skill_id = s.id WHERE s.name IN (:search) ", nativeQuery = true)
  public List<CVSkill> getSkillBySearch(@Param("search") List<String> search);

  @Modifying
  @Query(value = "DELETE FROM tb_cv_skill WHERE cv_id = :cvPersonId AND skill_id = :skillId", nativeQuery = true)
  void deleteByCvPersonIdAndSkillId(@Param("cvPersonId") Integer cvPersonId,
      @Param("skillId") Integer skillId);

}