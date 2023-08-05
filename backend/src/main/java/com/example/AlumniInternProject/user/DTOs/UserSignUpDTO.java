package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserSignUpDTO {
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private String phoneNumber;
    private String city;
    private Country country;
    private String password;
    private String bio;
    private Set<Skill> skills;
    private Set<Interest> interests;
    private Set<EducationHistory> educationHistories;
}
