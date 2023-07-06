package com.example.AlumniInternProject.user;

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



    @PostMapping("signup")
    public UserGetDto save(@RequestParam UserDTO dto, @RequestParam("profilePicUrl") MultipartFile multipartFile) throws IOException {

//    @PostMapping("signup")
//    public UserGetDto save(@RequestParam UserDTO dto, @RequestParam("profilePicUrl") MultipartFile multipartFile) throws IOException {

//        if(!multipartFile.isEmpty()){
//            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            dto.setProfilePicUrl(filename);
//            UserDTO savedUser = userService.save(dto);
//            String uploadDir = "user-photos/" + savedUser.getFirstname();
//            FileUploadUtil.cleanDir(uploadDir);
//            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
//
//        }else {
//            if (dto.getProfilePicUrl().isEmpty()) dto.setProfilePicUrl(null);

//
//            userService.save(dto);
//        }
//        dto.setProfilePicUrl("https://i.pravatar.cc/300");
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

    @GetMapping("{id}/enabled/{status}")
    public String updateEnabledStatus(@PathVariable("id") UUID id, @PathVariable("status") boolean status) {
        userService.updateEnabledStatus(id, status);
        String statuss = status ? "enabled" : "disabled";
        String message = "The user id " + id + " has been " + statuss;
        return message;
    }


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
