package com.example.AlumniInternProject.user.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPassDTO {
    private String token;
    private String newPassword;
}
