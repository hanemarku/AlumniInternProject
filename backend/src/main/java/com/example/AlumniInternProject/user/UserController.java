package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.FileUploadUtil;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.ExceptionHandling;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import com.example.AlumniInternProject.user.security.AlumniAuthenticationProvider;
import com.example.AlumniInternProject.user.utility.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

import static com.example.AlumniInternProject.constants.SecurityConstants.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends ExceptionHandling {
    private final UserService userService;


//    @Autowired
//    private AlumniAuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

//    private final DaoAuthenticationProvider authenticationProvider;

    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody UserLoginDTO user) throws UserNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        User loginUser = userService.findUserByEmail(user.getEmail());
        ALumniUserDetails userDetails = new ALumniUserDetails(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userDetails);
        return new ResponseEntity<>(loginUser,jwtHeader, OK);

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
    public List<UserGetDto> getAllUsers() {
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
    public ResponseEntity<Map<String, String>> save(@RequestBody UserDTO dto) throws UserNotFoundException, EmailExistException, MessagingException {
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("profilePicUrl") MultipartFile multipartFile, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws IOException {
        if (!multipartFile.isEmpty()) {
            Random random = new Random();
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String uniqueIdentifier = firstname + "-" + lastname+ "-" + random.nextInt(100000);
            String uploadDir = "user-photos/" + uniqueIdentifier;
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
            String fileUrl = "/user-photos/" + uniqueIdentifier + "/" + filename;
            return ResponseEntity.ok(fileUrl);
        } else {
            return ResponseEntity.badRequest().body("No file uploaded");
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




    private HttpHeaders getJwtHeader(ALumniUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, "Bearer " + jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }

    private void authenticate(String email, String password) {
//        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    }


}
