package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api")
public class PersonRestController {
    private PersonRepository personRepository;

    @Autowired
    public PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", personRepository.findAll());
    }

    @PostMapping("/post")
    public ResponseEntity<Object> postMethodName() {
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}