package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class UniversityRestController {
    private UniversityRepository universityRepository;

    @Autowired
    public UniversityRestController(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @GetMapping("university")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", universityRepository.findAll());
    }

    @PostMapping("university")
    public ResponseEntity<Object> post(@RequestBody University university) {
        universityRepository.save(university);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}