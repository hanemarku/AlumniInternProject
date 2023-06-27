package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.Interest;
import com.example.AlumniInternProject.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<String> getAllUsers() {
//        int savedUsers = userService.findAll().size();
//        if (savedUsers != 0) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("there are " + savedUsers + " users in the database" );
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list users");
//        }
//    }

    @GetMapping
    public List<UserGetDto> getAllUsers() {
        return userService.findAll();
    }



    @PostMapping
    public UserGetDto save(@RequestBody UserDTO dto) {
        return userService.save(dto);
    }

//    @PostMapping
//    public ResponseEntity<String> save(@RequestBody UserDTO dto) {
//        UserGetDto savedUser = userService.save(dto);
//        if (savedUser != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("User saved successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save user");
//        }
//    }
}
