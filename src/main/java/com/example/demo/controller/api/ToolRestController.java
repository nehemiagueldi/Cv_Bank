package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Tool;
import com.example.demo.repository.ToolRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api")
public class ToolRestController {
  private ToolRepository toolRepository;

  @Autowired
  public ToolRestController(ToolRepository toolRepository){
    this.toolRepository = toolRepository;
  }

  @GetMapping("/tool")
  public ResponseEntity<Object> getTool() {
    return CustomResponse.generate(HttpStatus.OK, "Data Found", toolRepository.findAll());
  }

  @PostMapping("/add/tool")
  public ResponseEntity<Object> addTool(@RequestBody Tool tool) {
    toolRepository.save(tool);
    return CustomResponse.generate(HttpStatus.OK, "Data Saved", tool);
  }
}
