package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Project;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class ProjectRestController {
    private ProjectRepository projectRepository;
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public ProjectRestController(ProjectRepository projectRepository, CVPersonRepository cvPersonRepository) {
        this.projectRepository = projectRepository;
        this.cvPersonRepository = cvPersonRepository;
    }

    @GetMapping("/project")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", projectRepository.findAll());
    }

    @PostMapping("/project")
    public ResponseEntity<Object> post(@RequestBody Project project) {
        project.setCvPerson(cvPersonRepository.findById(project.getCvPerson().getId()).get());
        projectRepository.save(project);

        CVPerson cvPerson = cvPersonRepository.findById(project.getCvPerson().getId()).get();
        cvPerson.setPercentage_progress(cvPerson.getPercentage_progress()+20);
        cvPersonRepository.save(cvPerson);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

}
