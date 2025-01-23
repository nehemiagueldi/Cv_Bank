package com.example.demo.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduDTO {
    private Double gpa;
    private String university;
    private String degree;
    private String major;
    private LocalDate startDate;
    private LocalDate endDate;
}
