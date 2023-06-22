package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
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
    private Date birthday;
    @Column(length = 64)
    private String profilePicUrl;
    @Column(length = 15)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @Column(length = 64, nullable = false)
    private String password;

    @Lob
    @Column( length = 1000)
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_skills",
            joinColumns= @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
