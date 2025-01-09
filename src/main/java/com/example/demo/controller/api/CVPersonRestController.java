package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api")
public class CVPersonRestController {
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public CVPersonRestController(CVPersonRepository cvPersonRepository) {
        this.cvPersonRepository = cvPersonRepository;
    }

    @GetMapping("/cvperson")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", cvPersonRepository.findAll());
    }

    @CrossOrigin
    @GetMapping("/cvperson/{id}")
    public ResponseEntity<Object> getCvPersonById(@PathVariable Integer id){
        CVPerson cvPerson = cvPersonRepository.findById(id).orElse(null);
        if (cvPerson != null) {
            return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", cvPerson);
        } else {
            return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
        }
    }

}
