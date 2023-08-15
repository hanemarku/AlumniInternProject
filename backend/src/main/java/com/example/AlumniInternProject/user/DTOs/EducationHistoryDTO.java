package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.Country;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EducationHistoryDTO {
    private String institutionName;
    private String fieldOfQualification;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private double finalGrade;
    private String website;
    private String city;
    private Country country;

    public EducationHistoryDTO(String institutionName, String fieldOfQualification, String fieldOfStudy, LocalDate startDate, LocalDate endDate, double finalGrade, String website, String city, Country country) {
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
