package com.example.AlumniInternProject.education;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EducationDto {

    private String institutionName;
    private String fieldOfQualification;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private double finalGrade;
    private String website;

}
