package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Degree;
import com.example.demo.repository.DegreeRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/degree")
public class DegreeRestController {
    private DegreeRepository degreeRepository;

    @Autowired
    public DegreeRestController(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", degreeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Degree degree) {
        degreeRepository.save(degree);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Degree degreeEdit) {
        Degree degree = degreeRepository.findById(id).get();
        degree.setName(degreeEdit.getName());
        degreeRepository.save(degree);
        return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        degreeRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
    }
}