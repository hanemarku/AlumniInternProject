package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
