package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.*;
import com.example.AlumniInternProject.enumerations.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter

public class UsersListingDTO extends IdBaseEntity {
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled;
    private LocalDate birthday;
    private String profilePicUrl;
    private String phoneNumber;
    private String city;
    private Country country;
    private String password;
    private String bio;
    private Set<Skill> skills;
    private Set<Interest> interests;
    private Set<Authority> authorities = new HashSet<>();
    private Role role;
    private List<EducationHistoryDTO> educationHistories;
    private List<EmploymentHistoryDTO> employmentHistories;

}

