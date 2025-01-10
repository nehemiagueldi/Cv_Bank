package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Tool;
import com.example.demo.repository.ToolRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api/tool")
public class ToolRestController {
  private ToolRepository toolRepository;

  @Autowired
  public ToolRestController(ToolRepository toolRepository) {
    this.toolRepository = toolRepository;
  }

  @GetMapping
  public ResponseEntity<Object> getTool() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", toolRepository.findAll());
  }

  @PostMapping("add")
  public ResponseEntity<Object> addTool(@RequestBody Tool tool) {
    toolRepository.save(tool);
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", tool);
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Tool toolEdit) {
    Tool tool = toolRepository.findById(id).get();
    tool.setName(toolEdit.getName());
    toolRepository.save(tool);
    return CustomResponse.generate(HttpStatus.OK, "Updated Data Successfully");
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id) {
    toolRepository.deleteById(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted Data Successfully");
  }
}
