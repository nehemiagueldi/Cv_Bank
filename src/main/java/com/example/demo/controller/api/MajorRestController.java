package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Major;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.MajorRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class MajorRestController {
    private MajorRepository majorRepository;
    private FacultyRepository facultyRepository;

    @Autowired
    public MajorRestController(MajorRepository majorRepository, FacultyRepository facultyRepository) {
        this.majorRepository = majorRepository;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping("/major")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", majorRepository.findAll());
    }

    @PostMapping("/major")
    public ResponseEntity<Object> post(@RequestBody Major major) {
        major.setFaculty(facultyRepository.findById(major.getFaculty().getId()).get());
        majorRepository.save(major);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}