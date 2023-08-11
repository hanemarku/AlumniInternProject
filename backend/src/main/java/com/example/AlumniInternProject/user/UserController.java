package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.FileUploadUtil;
import com.example.AlumniInternProject.Verfication.VerificationTokenRepository;
import com.example.AlumniInternProject.Verfication.VerificationTokenService;
import com.example.AlumniInternProject.entity.EducationHistory;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.ExceptionHandling;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.*;
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
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    private final VerificationTokenRepository verificationTokenRepository;

    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;

    private final UserRepository userRepo;
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    private HttpHeaders getJwtHeader(ALumniUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, "Bearer " + jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }


    @GetMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyEmail(@RequestParam("token") String token) throws UserNotFoundException, EmailExistException {
        Map<String, Boolean> response = new HashMap<>();
        boolean isActivated = false;
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User user = verificationToken.getUser();
        System.out.println(verificationToken);

        if (!user.isEnabled() || token != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (verificationToken.getExpirationDate().before(timestamp)) {
                isActivated = false;
            }else {
                user.setEnabled(true);
                userRepo.save(user);
                isActivated = true;
            }
        }
        response.put("isActivated", isActivated);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

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


//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyUser(@RequestBody VerificationRequest request) {
//        User user = userRepository.findByVerificationCode(request.getVerificationCode());
//        if (user != null) {
//            user.setEnabled(true);
//            userRepository.save(user);
//            return ResponseEntity.ok("User verified successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
//        }
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
            User user = userService.get(id);
            verificationTokenService.deleteAllByUser(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        //remove all tokens of the user

        userService.delete(id);
    }

    @GetMapping("{id}/enabled/{status}")
    public ResponseEntity<User> updateEnabledStatus(@PathVariable("id") UUID id, @PathVariable("status") boolean status) throws UserNotFoundException {
        userService.updateEnabledStatus(id, status);
        User user = userService.get(id);
        user.setEnabled(status);
        return ResponseEntity.ok().body(user);
    }



    @GetMapping("/email")
    public UsersListingDTO getUserByEmail(@RequestParam("email") String email) throws UserNotFoundException {
        return userService.findByEmail(email);
    }


    private void authenticate(String email, String password) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }


}
