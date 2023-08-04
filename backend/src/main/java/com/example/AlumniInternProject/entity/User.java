package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.enumerations.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends IdBaseEntity implements Serializable {

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

    private String token;

    @ManyToMany()

    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_not_locked", nullable = true)
    private boolean isNotLocked;

    @Column(length = 45)
    private String city;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


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

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "role_id")
//    private Role role;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserEvents> userEvents;

    @OneToMany(mappedBy = "requester")
    private List<ConnectionRequest> sentConnectionRequests;

    @OneToMany(mappedBy = "requestee")
    private List<ConnectionRequest> receivedConnectionRequests;


    @OneToMany(mappedBy = "recommender")
    private Set<Recommendation> recommender;

    @OneToMany(mappedBy = "recommendedUser")
    private Set<Recommendation> recommendedUser;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmploymentHistory> employmentHistories = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EducationHistory> educationHistories = new HashSet<>();


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userLikes")
    private Collection<Like> like;


    @Transient
    public String getImagePath() {
        if (super.getId() == null) return "/images/image-thumbnail.png";

        return "/user-images/" + super.getId() + "/" + this.profilePicUrl;
    }


    public User(String firstname, String lastname, String email, boolean enabled, LocalDate birthday, String profilePicUrl, String phoneNumber, String city, Country country, String password, String bio, Set<Skill> skills, Set<Interest> interests, Role role, Set<EmploymentHistory> employmentHistories, Set<EducationHistory> educationHistories, Set<Authority> authorities, boolean isActive, boolean isNotLocked) {
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
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public Set<EducationHistory> getEducationHistories() {
        Set<EducationHistory> educationHistories = new HashSet<>();
        for (EducationHistory educationHistory : this.educationHistories) {
            EducationHistory educationHistory1 = new EducationHistory();
            educationHistory1.setId(educationHistory.getId());
            educationHistory1.setFieldOfQualification(educationHistory.getFieldOfQualification());
            educationHistory1.setInstitutionName(educationHistory.getInstitutionName());
            educationHistory1.setFieldOfStudy(educationHistory.getFieldOfStudy());
            educationHistory1.setStartDate(educationHistory.getStartDate());
            educationHistory1.setEndDate(educationHistory.getEndDate());
            educationHistories.add(educationHistory1);
        }
        return educationHistories;
    }
}
