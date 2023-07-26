package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationHistory extends IdBaseEntity{
    @Column(length = 200, nullable = false)
    private String institutionName;

    @Column(length = 150, nullable = false)
    private String fieldOfQualification;

    @Column(length = 150, nullable = false)
    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    private double finalGrade;

    @Column(length = 100, nullable = false)
    private String website;

    @Column(length = 45)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EducationHistory(String institutionName, String fieldOfQualification, String fieldOfStudy, LocalDate startDate, LocalDate endDate, double finalGrade, String website, String city, Country country) {
        this.institutionName = institutionName;
        this.fieldOfQualification = fieldOfQualification;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalGrade = finalGrade;
        this.website = website;
        this.city = city;
        this.country = country;
    }
}
