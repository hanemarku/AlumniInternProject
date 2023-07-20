package com.example.AlumniInternProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
//@RequestMapping("")
//@RequiredArgsConstructor
//@EntityScan({"com.example.AlumniInternProject.entity"})
@OpenAPIDefinition(info = @Info(title = "Alumni Intern Project API", version = "1.0", description = "Alumni Intern Project API v1.0"))

public class AlumniInternProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumniInternProjectApplication.class, args);
	}



}
