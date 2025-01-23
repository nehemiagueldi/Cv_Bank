package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CVTool;

@Repository
public interface CVToolRepository extends JpaRepository<CVTool, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_cv_tool WHERE cv_id = ?", nativeQuery = true)
  public Long countByCVId(Integer cvId);

  @Query(value = "SELECT * FROM tb_cv_tool WHERE cv_id = ?", nativeQuery = true)
  public List<CVTool> getByCVId(@Param("id") Integer id);

  @Modifying
  @Query(value = "DELETE FROM tb_cv_tool WHERE cv_id = :cvPersonId AND tool_id = :toolId", nativeQuery = true)
  void deleteByCvPersonIdAndToolId(@Param("cvPersonId") Integer cvPersonId,
      @Param("toolId") Integer toolId);
}