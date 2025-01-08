package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Degree;
import com.example.demo.repository.DegreeRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class DegreeRestController {
    private DegreeRepository degreeRepository;

    @Autowired
    public DegreeRestController(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @GetMapping("degree")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", degreeRepository.findAll());
    }

    @PostMapping("degree")
    public ResponseEntity<Object> post(@RequestBody Degree degree) {
        degreeRepository.save(degree);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}