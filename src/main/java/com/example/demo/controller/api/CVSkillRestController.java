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
import com.example.demo.model.CVSkill;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVSkillRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api")
public class CVSkillRestController {
  private SkillRepository skillRepository;
  private CVSkillRepository cvSkillRepository;
  private CVPersonRepository cvPersonRepository;

  @Autowired
  public CVSkillRestController(SkillRepository skillRepository, CVSkillRepository cvSkillRepository, CVPersonRepository cvPersonRepository){
    this.skillRepository = skillRepository;
    this.cvSkillRepository = cvSkillRepository;
    this.cvPersonRepository = cvPersonRepository;
  }

  @GetMapping("/cvskill")
  public ResponseEntity<Object> getSkill() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", cvSkillRepository.findAll());
  }

  @PostMapping("/add/cvskill")
  public ResponseEntity<Object> addSkillPerson(@RequestBody CVSkill cvSkill) {
    cvSkill.setCvPerson(cvPersonRepository.findById(cvSkill.getCvPerson().getId()).get());
    cvSkill.setSkill(skillRepository.findById(cvSkill.getSkill().getId()).get());
    cvSkillRepository.save(cvSkill);

    if (cvSkillRepository.countByCVId(cvSkill.getCvPerson().getId()) == 1) {
      CVPerson cvPerson = cvPersonRepository.findById(cvSkill.getCvPerson().getId()).get();
      cvPerson.setPercentage_progress(cvPerson.getPercentage_progress()+10);
      cvPersonRepository.save(cvPerson);
    }

    return CustomResponse.generate(HttpStatus.OK, "Data Saved", cvSkill);
  }
}
