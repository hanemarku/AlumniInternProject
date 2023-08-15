package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.user.DTOs.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserGetDto extends UserDTO {
    private UUID id;


}
