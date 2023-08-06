package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.FileUploadUtil;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.ExceptionHandling;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.UserDTO;
import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import com.example.AlumniInternProject.user.DTOs.UsersListingDTO;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import com.example.AlumniInternProject.user.security.AlumniAuthenticationProvider;
import com.example.AlumniInternProject.user.utility.JWTTokenProvider;
import com.example.AlumniInternProject.user.utility.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.AlumniInternProject.constants.FileConstants.*;
import static com.example.AlumniInternProject.constants.SecurityConstants.JWT_TOKEN_HEADER;

//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(exposedHeaders = "jwt-token")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends ExceptionHandling {
    private final UserService userService;

    @Autowired
    private AlumniAuthenticationProvider authenticationProvider;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> signin(@RequestBody UserLoginDTO user) {
        try {
            authenticate(user.getEmail(), user.getPassword());
            User loginUser = userService.findUserByEmail(user.getEmail());
            ALumniUserDetails userDetails = new ALumniUserDetails(loginUser);
            HttpHeaders jwtHeader = getJwtHeader(userDetails);
            return ResponseEntity.ok().headers(jwtHeader).body(loginUser);
        } catch (AuthenticationException ex) {
            // If authentication fails, return a 401 Unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }




    private HttpHeaders getJwtHeader(ALumniUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, "Bearer " + jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }


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
    public List<UsersListingDTO> getAllUsers() {

        return userService.findAll();
    }




//    @PostMapping(value = "/signup")
//    public ResponseEntity<Map<String, String>>  save(@RequestBody UserDTO dto, @RequestParam("profilePicUrl") MultipartFile multipartFile) throws IOException {
//        System.out.println(dto);
//        System.out.println(multipartFile);
//        Map<String, String> response = new HashMap<>();
//        UserDTO savedUser;
//        if (!multipartFile.isEmpty()) {
//            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            dto.setProfilePicUrl(filename);
//            savedUser = userService.save(dto);
//            String uploadDir = "user-photos/" + savedUser.getFirstname();
//            FileUploadUtil.cleanDir(uploadDir);
//            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
//        } else {
//            if (dto.getProfilePicUrl().isEmpty()) dto.setProfilePicUrl(null);
//            userService.save(dto);
//        }
//        response.put("message", "User saved successfully");
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//
//
//    }



    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> save(@RequestBody UserDTO dto) throws UserNotFoundException, EmailExistException {
        UserGetDto savedUser = userService.save(dto);
        Map<String, String> response = new HashMap<>();

        if (savedUser != null) {
            response.put("message", "User saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Failed to save user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get-profile-pic")
    @CrossOrigin
    public ResponseEntity<byte[]> getProfilePic(@RequestParam("email") String email) throws IOException {
        System.out.println(email);
        User user = userService.findUserByEmail(email);
        String path = userService.fixProfileImagePath(user.getProfilePicUrl());

        Path imagePath = Paths.get(path);
        System.out.println(imagePath);
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("profilePicUrl") MultipartFile multipartFile, @RequestParam("email")  String email) throws IOException {
        if (!multipartFile.isEmpty()) {

            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uniqueIdentifier = userService.generateRrofileImageUrl(email);
            String uploadDir = UPLOAD_DIRECTORY + uniqueIdentifier;
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
            String fileUrl = "/" + UPLOAD_DIRECTORY + uniqueIdentifier + "/" + filename;
            return ResponseEntity.ok(fileUrl);
        } else {
            String fileUrl = "/" + TEMP_UPLOAD_DIRECTORY + "/" + TEMP_PROFILE_IMAGE_BASE_URL;
            return ResponseEntity.ok(fileUrl);

        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@RequestParam("email") String email) {
        boolean isEmailAvailable = userService.isEmailAvailable(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isEmailAvailable);
        return ResponseEntity.ok(response);
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

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) throws UserNotFoundException {
        try {
            userService.get(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        userService.delete(id);
    }

    @GetMapping("{id}/enabled/{status}")
    public ResponseEntity<User> updateEnabledStatus(@PathVariable("id") UUID id, @PathVariable("status") boolean status) throws UserNotFoundException {
        userService.updateEnabledStatus(id, status);
        User user = userService.get(id);
        user.setEnabled(status);
        return ResponseEntity.ok().body(user);
    }


    @GetMapping("/login")
    public String viewLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";

    }

    private void authenticate(String email, String password) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }


}
