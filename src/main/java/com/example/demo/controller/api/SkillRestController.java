package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class SkillRestController {
  private SkillRepository skillRepository;

  @Autowired
  public SkillRestController(SkillRepository skillRepository){
    this.skillRepository = skillRepository;
  }

  @GetMapping("/skill")
  public ResponseEntity<Object> getSkill() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", skillRepository.findAll());
  }

  @PostMapping("/add/skill")
  public ResponseEntity<Object> addSkill(@RequestBody Skill skill) {
    skillRepository.save(skill);
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", skill);
  }
  
}
