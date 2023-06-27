package com.example.AlumniInternProject.Employment;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.Skill;
import com.example.AlumniInternProject.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentHistory extends IdBaseEntity {

    @Column(length = 1500, nullable = false)
    @Lob
    private String mainActivities;

    @Column(length = 45, nullable = false)
    private String occupationPosition;

    @Column(length = 45, nullable = false)
    private String companyName;

    @Column(length = 45)
    private String department;

    private boolean ongoing;
    private LocalDate fromDate;
    private LocalDate toDate;

    /*Nje user ka shume histori punesimi , dhe 1 histori punesimi
     i perket 1 user ,pra marredhenia eshte 1 me shume */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_skills",
            joinColumns= @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    /*
    * Constructor with signature as the first dto created!*/
    public  EmploymentHistory(String mainActivities,
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
}
