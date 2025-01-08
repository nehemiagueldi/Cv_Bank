package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class FacultyRestController {
    private FacultyRepository facultyRepository;

    @Autowired
    public FacultyRestController(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @GetMapping("faculty")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", facultyRepository.findAll());
    }

    @PostMapping("faculty")
    public ResponseEntity<Object> post(@RequestBody Faculty faculty) {
        facultyRepository.save(faculty);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}