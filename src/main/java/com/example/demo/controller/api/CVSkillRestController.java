package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.CVSkill;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVSkillRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api/cv-skill")
public class CVSkillRestController {
  private SkillRepository skillRepository;
  private CVSkillRepository cvSkillRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public CVSkillRestController(SkillRepository skillRepository, CVSkillRepository cvSkillRepository,
      CVPersonRepository cvPersonRepository) {
    this.skillRepository = skillRepository;
    this.cvSkillRepository = cvSkillRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @GetMapping
  public ResponseEntity<Object> getSkill() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", cvSkillRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> addSkillPerson(@RequestBody CVSkill cvSkill) {
    cvSkill.setCvPerson(cvPersonRepository.findById(cvSkill.getCvPerson().getId()).get());
    cvSkill.setSkill(skillRepository.findById(cvSkill.getSkill().getId()).get());
    cvSkillRepository.save(cvSkill);

    if (cvSkillRepository.countByCVId(cvSkill.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvSkill.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() + 10);
      cvPersonRepository.save(cvPerson);
    }
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", cvSkill);
  }

  @PostMapping("edit/{id}")
  public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody CVSkill cvSkillEdit) {
    CVSkill cvSkill = cvSkillRepository.findById(id).get();
    if (cvSkill.getCvPerson().getId() == cvSkillEdit.getCvPerson().getId()) {
      cvSkill.setSkill(skillRepository.findById(cvSkillEdit.getSkill().getId()).get());
      cvSkillRepository.save(cvSkill);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Not Found");
    }
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", cvSkill);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestParam("cvId") Integer cvId) {
    if (cvSkillRepository.countByCVId(cvId) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvId).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress() - 10);
      cvPersonRepository.save(cvPerson);
    }
    cvSkillRepository.deleteById(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
  }
}
