package com.example.AlumniInternProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;

import static com.example.AlumniInternProject.constants.FileConstants.USER_FOLDER;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Alumni Intern Project API", version = "1.0", description = "Alumni Intern Project API v1.0"))
@EnableWebMvc
////scan the component AuthenticationFailureListener and AuthenticationSuccessEventListener
//@ComponentScan(basePackages = {"com.example.AlumniInternProject.user.listener"})
////scan LoginAttemptService
//@ComponentScan(basePackages = {"com.example.AlumniInternProject.user"})

 // Update the package for entity scan
public class AlumniInternProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumniInternProjectApplication.class, args);
		new File(USER_FOLDER).mkdirs();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}


}
