package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // define sebuah table didatabase
@Table(name = "tb_cv_person")
@Data // anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor // constructor dengan parameter semua properti
@NoArgsConstructor // constructor tidak menggunakan parameter
public class CVPerson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = true)
  private String photo_profile;
  @Column(length = 80, nullable = true)
  private String position;
  @Column(columnDefinition = "TEXT", length = 2000, nullable = true)
  private String summary;
  private Double percentage_progress;
  private String randomString;
  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person person;

  @OneToMany(mappedBy = "cvPerson")
  private List<CVSkill> cvSkills;
  @OneToMany(mappedBy = "cvPerson")
  private List<CVTool> cvTools;
  @OneToMany(mappedBy = "cvPerson")
  private List<Education> educations;
  @OneToMany(mappedBy = "cvPerson")
  private List<Project> projects;
  @OneToMany(mappedBy = "cvPerson")
  private List<Training> trainings;
  @OneToMany(mappedBy = "cvPerson")
  private List<WorkExp> workExps;
}
