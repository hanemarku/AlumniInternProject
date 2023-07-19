package com.example.AlumniInternProject.Employment.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
