package com.example.AlumniInternProject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AlumniInternProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumniInternProjectApplication.class, args);
	}

	@RequestMapping("")
	public String hello() {
		return "Hello World";
	}
}
