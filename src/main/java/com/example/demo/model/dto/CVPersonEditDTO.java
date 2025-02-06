package com.example.demo.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Education;
import com.example.demo.model.Project;
import com.example.demo.model.Training;
import com.example.demo.model.WorkExp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVPersonEditDTO {
    private String name;
    // private Character gender;
    // private LocalDate birthdate;
    // private String photo_profile;
    private String position;
    private String summary;
    private List<Integer> skillsSelected;
    private List<Integer> toolsSelected;
    private List<WorkExp> workExp;
    private List<Project> projects;
    private List<Training> trainings;
    private List<Education> educations;
}
