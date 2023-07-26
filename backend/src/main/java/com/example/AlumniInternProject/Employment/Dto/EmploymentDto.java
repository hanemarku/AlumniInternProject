package com.example.AlumniInternProject.Employment.Dto;

import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {

    private String mainActivities;
    private String occupationPosition;
    private String companyName;
    private String department;
    private boolean ongoing;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String city;
    private Country country;
    private Set<Skill> skills = new HashSet<>();



    /*
    *     public  EmploymentDto(String mainActivities,
                          String occupationPosition,
                          String companyName,String department,
                          boolean ongoing, LocalDate fromDate , LocalDate toDate){
        this.mainActivities = mainActivities;
        this.occupationPosition = occupationPosition;
        this.companyName = companyName;
        this.department = department;
        this.ongoing = ongoing;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
*/
}
