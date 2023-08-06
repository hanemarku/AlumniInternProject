package com.example.AlumniInternProject.user.DTOs;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmploymentHistoryDTO {
    private String mainActivities;
    private String occupationPosition;
    private String companyName;
    private String department;
    private boolean ongoing;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String city;
    private Country country;
    private Set<Skill> skills;

    public EmploymentHistoryDTO(String mainActivities, String occupationPosition, String companyName, String department, boolean ongoing, LocalDate fromDate, LocalDate toDate,  String city, Country country, Set<Skill> skills) {
        this.mainActivities = mainActivities;
        this.occupationPosition = occupationPosition;
        this.companyName = companyName;
        this.department = department;
        this.ongoing = ongoing;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.city = city;
        this.country = country;
        this.skills = skills;
    }
}
