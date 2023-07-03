package com.example.AlumniInternProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends IdBaseEntity{

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstname;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastname;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    private boolean  enabled;

    private LocalDate birthday;

    @Column(length = 64)
    private String profilePicUrl;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 45)
    private String city;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    //    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "city_id")
//
//    private City city;
    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 1500)
    private String bio;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_skills",
            joinColumns= @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;



    public User(String firstname, String lastname, String email, boolean enabled, LocalDate birthday, String profilePicUrl, String phoneNumber, String city, Country country, String password, String bio, Set<Skill> skills, Set<Interest> interests, Role role) {
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

    public User() {
    }

    @Transient
    public String getImagePath() {
        if (super.getId() == null) return "/images/image-thumbnail.png";

        return "/user-images/" + super.getId() + "/" + this.profilePicUrl;
    }


}