package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Training;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.TrainingRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api/training")
public class TrainingRestController {
  private TrainingRepository trainingRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public TrainingRestController(TrainingRepository trainingRepository, CVPersonRepository cvPersonRepository) {
    this.trainingRepository = trainingRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @CrossOrigin
  @GetMapping
  public ResponseEntity<Object> get() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", trainingRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> post(@RequestBody Training training) {
    training.setCvPerson(cvPersonRepository.findById(training.getCvPerson().getId()).get());
    trainingRepository.save(training);

    if (trainingRepository.countByCVId(training.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(training.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 20);
      cvPersonRepository.save(cvPerson);
    }
    return CustomResponse.generate(HttpStatus.OK, "Data Saved");
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Training trainingEdit) {
    Training training = trainingRepository.findById(id).get();
    if (training.getCvPerson().getId() == trainingEdit.getCvPerson().getId()) {
      training.setName(trainingEdit.getName());
      training.setDescription(trainingEdit.getDescription());
      training.setCompany(trainingEdit.getCompany());
      training.setStart_date(trainingEdit.getStart_date());
      training.setEnd_date(trainingEdit.getEnd_date());
      trainingRepository.save(training);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Not Found");
    }

    return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestParam("cvId") Integer cvId) {
    if (trainingRepository.countByCVId(cvId) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvId).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() - 20);
      cvPersonRepository.save(cvPerson);
    }
    trainingRepository.deleteById(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
  }

}
