package com.example.demo.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.CVPerson;
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
    private CVPerson cvPerson;
    private List<Project> projects;
    // private List<Training> trainings;
    // private List<WorkExp> workExpsA;
}
