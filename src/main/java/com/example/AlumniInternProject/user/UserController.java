package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.Interest;
import com.example.AlumniInternProject.entity.Skill;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        List<Country> countries = userService.listAllCountries();
        return userService.save(dto);
    }

    @PostMapping("check_unique_email")
    public String isEmailUnique( @RequestParam String email) {
        UUID id =   UUID.randomUUID();
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }

    @PatchMapping("edit/{id}")
    public UserGetDto update(@PathVariable("id") UUID id, @RequestBody UserDTO user) throws UserNotFoundException {
        try {
            userService.get(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return userService.update(id, user);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") UUID id) throws UserNotFoundException {
        try {
            userService.get(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        userService.delete(id);
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
