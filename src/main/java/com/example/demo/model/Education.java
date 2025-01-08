package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // define sebuah table didatabase
@Table(name = "tb_education")
@Data // anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor // constructor dengan parameter semua properti
@NoArgsConstructor // constructor tidak menggunakan parameter
public class Education {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id;
  private Double gpa;
  private LocalDate startDate;
  private LocalDate endDate;
  @ManyToOne
  @JoinColumn(name = "university_id", referencedColumnName = "id")
  private University university;
  @ManyToOne
  @JoinColumn(name = "degree_id", referencedColumnName = "id")
  private Degree degree;
  @ManyToOne
  @JoinColumn(name = "major_id", referencedColumnName = "id")
  private Major major;
  @ManyToOne
  @JoinColumn(name = "cv_id", referencedColumnName = "id")
  private CVPerson cvPerson;
}
