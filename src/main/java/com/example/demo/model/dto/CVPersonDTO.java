package com.example.demo.model.dto;

import java.util.List;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Education;
import com.example.demo.model.Project;
import com.example.demo.model.Training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVPersonDTO {
    private CVPerson cvPerson;
    private PersonDTO projects;
    // private Education education;

}
