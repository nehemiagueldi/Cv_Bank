package com.example.demo.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.CVPerson;
import com.example.demo.model.CVSkill;
import com.example.demo.model.CVTool;
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
public class CVPersonDTO {
    private Integer totalExperience;
    private Integer age;
    private CVPerson cvPerson;
    private List<Project> projects = new ArrayList<>();;
    private List<Education> educations = new ArrayList<>();
    private List<WorkExp> workExps = new ArrayList<>();
    private List<Training> trainings = new ArrayList<>();
    private List<CVTool> cvTools = new ArrayList<>();
    private List<CVSkill> cvSkills = new ArrayList<>();

}
