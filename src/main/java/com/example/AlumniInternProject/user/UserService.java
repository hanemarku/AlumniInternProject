package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserGetDto save(UserDTO userDto);
    List<UserGetDto> findAll();
    User findById(UUID id);
    UserGetDto update(UUID id, UserDTO dto);
    void delete(UUID id);
    List<Country> listAllCountries();
    boolean isEmailUnique(UUID id, String email);
    User get(UUID id) throws UserNotFoundException;

}
