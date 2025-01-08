package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.CVPersonRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api")
public class CVPersonRestController {
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public CVPersonRestController(CVPersonRepository cvPersonRepository) {
        this.cvPersonRepository = cvPersonRepository;
    }

    @GetMapping("/CVPerson")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", cvPersonRepository.findAll());
    }

}
