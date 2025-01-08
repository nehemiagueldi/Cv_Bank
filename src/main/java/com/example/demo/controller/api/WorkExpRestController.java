package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.WorkExp;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.WorkExpRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class WorkExpRestController {
    private WorkExpRepository workExpRepository;
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public WorkExpRestController(WorkExpRepository workExpRepository, CVPersonRepository cvPersonRepository) {
        this.workExpRepository = workExpRepository;
        this.cvPersonRepository = cvPersonRepository;
    }

    @GetMapping("work-exp")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", workExpRepository.findAll());
    }

    @PostMapping("work-exp")
    public ResponseEntity<Object> post(@RequestBody WorkExp workExp) {
        workExp.setCvPerson(cvPersonRepository.findById(workExp.getCvPerson().getId()).get());
        workExpRepository.save(workExp);

        if (workExpRepository.countByCVId(workExp.getCvPerson().getId()) == 1) {
            CVPerson cvPerson = cvPersonRepository.findById(workExp.getCvPerson().getId()).get();
            cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 20);
            cvPersonRepository.save(cvPerson);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data Save");
    }

}
