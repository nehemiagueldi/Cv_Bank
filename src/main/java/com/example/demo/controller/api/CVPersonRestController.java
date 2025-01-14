package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;

import com.example.demo.model.dto.CVPersonDTO;

import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVSkillRepository;
import com.example.demo.repository.CVToolRepository;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TrainingRepository;
import com.example.demo.repository.WorkExpRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("api/cv-person")
public class CVPersonRestController {
    private CVPersonRepository cvPersonRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private EducationRepository educationRepository;
    private WorkExpRepository workExpRepository;
    private TrainingRepository trainingRepository;
    private CVToolRepository cvToolRepository;
    private CVSkillRepository cvSkillRepository;

    @Autowired
    public CVPersonRestController(CVPersonRepository cvPersonRepository, PersonRepository personRepository,
            ProjectRepository projectRepository, EducationRepository educationRepository,
            WorkExpRepository workExpRepository, TrainingRepository trainingRepository,
            CVToolRepository cvToolRepository, CVSkillRepository cvSkillRepository) {
        this.cvPersonRepository = cvPersonRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.workExpRepository = workExpRepository;
        this.trainingRepository = trainingRepository;
        this.cvToolRepository = cvToolRepository;
        this.cvSkillRepository = cvSkillRepository;
    }

    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", cvPersonRepository.findAll());
    }

    @GetMapping("{randomString}")
    public CVPersonDTO get(@PathVariable String randomString) {
        CVPerson cvPerson = cvPersonRepository.getCVByRS(randomString);
        return new CVPersonDTO(cvPerson, projectRepository.getByCVId(cvPerson.getId()),
                educationRepository.getByCVId(cvPerson.getId()), workExpRepository.getByCVId(cvPerson.getId()), trainingRepository.getByCVId(cvPerson.getId()), cvToolRepository.getByCVId(cvPerson.getId()), cvSkillRepository.getByCVId(cvPerson.getId()));
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody CVPerson cvPerson) {
        cvPerson.setPerson(personRepository.findById(cvPerson.getPerson().getId()).get());
        cvPersonRepository.save(cvPerson);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }
}
