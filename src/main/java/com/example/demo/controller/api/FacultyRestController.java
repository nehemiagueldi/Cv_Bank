package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/faculty")
public class FacultyRestController {
    private FacultyRepository facultyRepository;

    @Autowired
    public FacultyRestController(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", facultyRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Faculty faculty) {
        facultyRepository.save(faculty);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Faculty facultyEdit) {
        Faculty faculty = facultyRepository.findById(id).get();
        faculty.setName(facultyEdit.getName());
        facultyRepository.save(faculty);
        return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        facultyRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
    }

}