package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Interest;
import com.example.AlumniInternProject.entity.Skill;
import com.example.AlumniInternProject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserGetDto> getAllUsers() {
        List<UserGetDto> users = userService.findAll();
        for (UserGetDto user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("First Name: " + user.getFirstname());
            System.out.println("Last Name: " + user.getLastname());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Country" + user.getCity().getCountry().getName());
            System.out.println("City" + user.getCity().getName());
            // Print other user details...

            // Print user's skills
            System.out.println("Skills:");
            for (Skill skill : user.getSkills()) {
                System.out.println("- " + skill.getName());
            }


            // Print user's interests
            System.out.println("Interests:");
            for (Interest interest : user.getInterests()) {
                System.out.println("- " + interest.getName());
            }

            System.out.println("-------------------------------------");
        }
        return userService.findAll();
    }
//    public List<UserGetDto> getAll() {
//        System.out.println(userService.findAll().size());
//        return userService.findAll();
//    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody UserDTO dto) {
        UserGetDto savedUser = userService.save(dto);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save user");
        }
    }
}
