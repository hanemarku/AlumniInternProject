package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserGetDto save(UserDTO userDto) throws UserNotFoundException, EmailExistException, MessagingException;
    List<UserGetDto> findAll();
    User findById(UUID id);
    UserGetDto update(UUID id, UserDTO dto) throws UserNotFoundException;
    void delete(UUID id) throws UserNotFoundException;
    List<Country> listAllCountries();

    User get(UUID id) throws UserNotFoundException;
    void updateEnabledStatus(UUID id, boolean status);
    boolean isEmailAvailable(String email);

//    UserDetails loadUserByUsername(String email) throws UserNotFoundException;

    User findUserByEmail(String email);

    User updateProfileImage(UUID id, MultipartFile profileImage) throws UserNotFoundException;
}
