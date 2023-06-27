package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserGetDto save(UserDTO userDto);
    List<UserGetDto> findAll();
    User findById(UUID id);
    User update(UUID id, User dto);
    void delete(UUID id);
}
