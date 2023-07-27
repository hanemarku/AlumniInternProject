package com.example.AlumniInternProject.education;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class EducationDto {
    private UUID id;
    private String institutionName;
    private String fieldOfQualification;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private double finalGrade;
    private String website;
    private String city;
    private Country country;
    private User user;
}
