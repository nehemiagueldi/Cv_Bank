package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/skill")
public class SkillRestController {
  private SkillRepository skillRepository;

  @Autowired
  public SkillRestController(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }

  @GetMapping
  public ResponseEntity<Object> getSkill() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", skillRepository.findAll());
  }

  @PostMapping("add")
  public ResponseEntity<Object> addSkill(@RequestBody Skill skill) {
    skillRepository.save(skill);
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", skill);
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Skill skillEdit) {
    Skill skill = skillRepository.findById(id).get();
    skill.setName(skillEdit.getName());
    skillRepository.save(skill);
    return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id) {
    skillRepository.deleteById(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
  }

}
