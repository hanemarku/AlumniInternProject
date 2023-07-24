package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Role role;

    public UserDTO() {

    }

    public UserDTO(String firstname, String lastname, String email, boolean enabled, LocalDate birthday, String profilePicUrl, String phoneNumber, String city, Country country, String password, String bio, Set<Skill> skills, Set<Interest> interests, Role role) {
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
    }

}
