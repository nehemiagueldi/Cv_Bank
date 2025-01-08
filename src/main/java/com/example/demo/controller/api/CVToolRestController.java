package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.CVTool;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVToolRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api")
public class CVToolRestController {
  private ToolRepository toolRepository;
  private CVToolRepository cvToolRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public CVToolRestController(ToolRepository toolRepository, CVToolRepository cvToolRepository, CVPersonRepository cvPersonRepository){
    this.toolRepository = toolRepository;
    this.cvToolRepository = cvToolRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @GetMapping("/cvtool")
  public ResponseEntity<Object> getTool() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", cvToolRepository.findAll());
  }

  @PostMapping("/add/cvtool")
  public ResponseEntity<Object> addToolPerson(@RequestBody CVTool cvTool) {
    cvTool.setCvPerson(cvPersonRepository.findById(cvTool.getCvPerson().getId()).get());
    cvTool.setTool(toolRepository.findById(cvTool.getTool().getId()).get());
    cvToolRepository.save(cvTool);

    if (cvToolRepository.countByCVId(cvTool.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvTool.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress()+10);
      cvPersonRepository.save(cvPerson);
    }

    return CustomResponse.generate(HttpStatus.OK, "Data Saved", cvTool);
  }
}
