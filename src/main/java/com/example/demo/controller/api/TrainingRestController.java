package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Training;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.TrainingRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api")
public class TrainingRestController {
  private TrainingRepository trainingRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public TrainingRestController(TrainingRepository trainingRepository, CVPersonRepository cvPersonRepository){
    this.trainingRepository = trainingRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @CrossOrigin
  @GetMapping("/training")
  public ResponseEntity<Object> get() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", trainingRepository.findAll());
  }
  
  @PostMapping("/add/training")
  public ResponseEntity<Object> post(@RequestBody Training training) {
    training.setCvPerson(cvPersonRepository.findById(training.getCvPerson().getId()).get());
    trainingRepository.save(training);

    if (trainingRepository.countByCVId(training.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(training.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress()+20);
      cvPersonRepository.save(cvPerson);
    }
    return CustomResponse.generate(HttpStatus.OK, "Data Saved");
  }

}
