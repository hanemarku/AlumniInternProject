package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.EmploymentHistory;
import com.example.AlumniInternProject.entity.*;
import com.example.AlumniInternProject.enumerations.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
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
    private Set<EmploymentHistory> employmentHistories;
    private Set<EducationHistory> educationHistories;
    private Set<Authority> authorities = new HashSet<>();
    private Role role;

    public UserDTO() {

    }

    public UserDTO(String firstname, String lastname, String email, boolean enabled, LocalDate birthday, String profilePicUrl, String phoneNumber, String city, Country country, String password, String bio, Set<Skill> skills, Set<Interest> interests, Role role, Set<EmploymentHistory> employmentHistories, Set<EducationHistory> educationHistories, Set<Authority> authorities) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled = enabled;
        this.birthday = birthday;
        this.profilePicUrl = profilePicUrl;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;
        this.password = password;
        this.bio = bio;
        this.skills = skills;
        this.interests = interests;
        this.role = role;
        this.employmentHistories = employmentHistories;
        this.educationHistories = educationHistories;
        this.authorities = authorities;
    }


}
