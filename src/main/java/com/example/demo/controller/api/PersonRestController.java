package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/person")
public class PersonRestController {
    private PersonRepository personRepository;

    @Autowired
    public PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", personRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Person person) {
        personRepository.save(person);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Person personEdit) {
        Person person = personRepository.findById(id).get();
        person.setName(personEdit.getName());
        person.setEmail(personEdit.getEmail());
        person.setPhone(personEdit.getPhone());
        person.setAddress(personEdit.getAddress());
        person.setBirthdate(personEdit.getBirthdate());
        person.setGender(personEdit.getGender());
        personRepository.save(person);
        return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        personRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
    }

}