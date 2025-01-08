package com.example.demo.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Education;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.DegreeRepository;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.MajorRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class EducationRestController {
    private EducationRepository educationRepository;
    private CVPersonRepository cvPersonRepository;
    private UniversityRepository universityRepository;
    private DegreeRepository degreeRepository;
    private MajorRepository majorRepository;

    @Autowired
    public EducationRestController(EducationRepository educationRepository, CVPersonRepository cvPersonRepository,
            UniversityRepository universityRepository, DegreeRepository degreeRepository,
            MajorRepository majorRepository) {
        this.educationRepository = educationRepository;
        this.cvPersonRepository = cvPersonRepository;
        this.universityRepository = universityRepository;
        this.degreeRepository = degreeRepository;
        this.majorRepository = majorRepository;
    }

    @GetMapping("education")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", educationRepository.findAll());
    }

    @PostMapping("education")
    public ResponseEntity<Object> post(@RequestBody Education education) {
        education.setUniversity(universityRepository.findById(education.getUniversity().getId()).get());
        education.setDegree(degreeRepository.findById(education.getDegree().getId()).get());
        education.setMajor(majorRepository.findById(education.getMajor().getId()).get());
        education.setCvPerson(cvPersonRepository.findById(education.getCvPerson().getId()).get());
        educationRepository.save(education);

        if (educationRepository.countByCVId(education.getCvPerson().getId()) == 1) {
            CVPerson cvPerson = cvPersonRepository.findById(education.getCvPerson().getId()).get();
            cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 20);
            cvPersonRepository.save(cvPerson);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data Save");
    }

}
