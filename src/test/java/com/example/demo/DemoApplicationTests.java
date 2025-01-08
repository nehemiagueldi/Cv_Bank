package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.RoleRepository;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void contextLoads() {
		assertEquals("admin", roleRepository.findById(1).orElse(null).getName());
	}

}
