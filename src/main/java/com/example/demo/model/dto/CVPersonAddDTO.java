package com.example.demo.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CVPersonAddDTO {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Character gender;
    private String randomString;
}
