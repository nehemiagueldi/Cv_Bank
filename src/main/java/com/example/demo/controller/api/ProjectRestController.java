package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Project;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/project")
public class ProjectRestController {
    private ProjectRepository projectRepository;
    private CVPersonRepository cvPersonRepository;

    @Autowired
    public ProjectRestController(ProjectRepository projectRepository, CVPersonRepository cvPersonRepository) {
        this.projectRepository = projectRepository;
        this.cvPersonRepository = cvPersonRepository;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", projectRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id) {
        return CustomResponse.generate(HttpStatus.OK, "Data Found", projectRepository.getByCVId(id));
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody Project project) {
        project.setCvPerson(cvPersonRepository.findById(project.getCvPerson().getId()).get());
        projectRepository.save(project);

        if (projectRepository.countByCVId(project.getCvPerson().getId()) == 1) {
            CVPerson cvPerson = cvPersonRepository.findById(project.getCvPerson().getId()).get();
            cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 20);
            cvPersonRepository.save(cvPerson);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody Project projectEdit) {
        Project project = projectRepository.findById(id).get();
        if (project.getCvPerson().getId() == projectEdit.getCvPerson().getId()) {
            project.setName(projectEdit.getName());
            project.setDescription(projectEdit.getDescription());
            project.setCompany(projectEdit.getCompany());
            project.setStart_date(projectEdit.getStart_date());
            project.setEnd_date(projectEdit.getEnd_date());
            projectRepository.save(project);
        } else {
            return CustomResponse.generate(HttpStatus.OK, "Data Not Found");
        }
        return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestParam("cvId") Integer cvId) {
        if (projectRepository.countByCVId(cvId) == 1) {
            CVPerson cvPerson = cvPersonRepository.findById(cvId).get();
            cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() - 20);
            cvPersonRepository.save(cvPerson);
        }
        projectRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
    }
}
