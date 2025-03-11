package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Major;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.MajorRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("api/major")
public class MajorRestController {
    private MajorRepository majorRepository;
    private FacultyRepository facultyRepository;

    @Autowired
    public MajorRestController(MajorRepository majorRepository, FacultyRepository facultyRepository) {
        this.majorRepository = majorRepository;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", majorRepository.findAll());
    }

    @GetMapping("{faculty}")
    public ResponseEntity<Object> getByFacultyId(@PathVariable Integer faculty) {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", majorRepository.getMajorByFacutlyId(faculty));
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Major major) {
        major.setFaculty(facultyRepository.findById(major.getFaculty().getId()).get());
        majorRepository.save(major);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Major majorEdit) {
        Major major = majorRepository.findById(id).get();
        major.setName(majorEdit.getName());
        major.setFaculty(facultyRepository.findById(majorEdit.getFaculty().getId()).get());
        majorRepository.save(major);
        return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        majorRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
    }

}