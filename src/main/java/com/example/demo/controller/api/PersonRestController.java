package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Person;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/person")
public class PersonRestController {
    private PersonRepository personRepository;
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public PersonRestController(PersonRepository personRepository, CVPersonRepository cvPersonRepository) {
        this.personRepository = personRepository;
        this.cvPersonRepository = cvPersonRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", personRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody RegisterDTO registerDTO) {
        Person person = new Person();
        person.setName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        person.setEmail(registerDTO.getEmail());
        person.setBirthdate(registerDTO.getBirthdate());
        person.setGender(registerDTO.getGender());
        personRepository.save(person);

        CVPerson cvPerson = new CVPerson();
        cvPerson.setPercentage_progress(0.0);
        cvPerson.setRandomString(registerDTO.getRandomString());
        cvPerson.setPerson(personRepository.findById(person.getId()).get());
        cvPersonRepository.save(cvPerson);

        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Person personEdit) {
        Person person = personRepository.findById(id).get();
        person.setName(personEdit.getName());
        person.setEmail(personEdit.getEmail());
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