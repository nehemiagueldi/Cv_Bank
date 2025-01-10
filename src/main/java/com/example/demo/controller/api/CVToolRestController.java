package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.CVTool;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVToolRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.utils.CustomResponse;

@CrossOrigin
@RestController
@RequestMapping("api/cv-tool")
public class CVToolRestController {
  private ToolRepository toolRepository;
  private CVToolRepository cvToolRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public CVToolRestController(ToolRepository toolRepository, CVToolRepository cvToolRepository,
      CVPersonRepository cvPersonRepository) {
    this.toolRepository = toolRepository;
    this.cvToolRepository = cvToolRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @GetMapping
  public ResponseEntity<Object> getTool() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", cvToolRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> addToolPerson(@RequestBody CVTool cvTool) {
    cvTool.setCvPerson(cvPersonRepository.findById(cvTool.getCvPerson().getId()).get());
    cvTool.setTool(toolRepository.findById(cvTool.getTool().getId()).get());
    cvToolRepository.save(cvTool);

    if (cvToolRepository.countByCVId(cvTool.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvTool.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 10);
      cvPersonRepository.save(cvPerson);
    }
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", cvTool);
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody CVTool cvToolEdit) {
    CVTool cvTool = cvToolRepository.findById(id).get();
    if (cvTool.getCvPerson().getId() == cvToolEdit.getCvPerson().getId()) {
      cvTool.setTool(toolRepository.findById(cvToolEdit.getTool().getId()).get());
      cvToolRepository.save(cvTool);
      return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Not Found");
    }
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestParam("cvId") Integer cvId) {
    if (cvToolRepository.countByCVId(cvId) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvId).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() - 10);
      cvPersonRepository.save(cvPerson);
    }
    cvToolRepository.deleteById(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
  }
}
