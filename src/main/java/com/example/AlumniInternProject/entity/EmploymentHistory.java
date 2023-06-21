package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class EmploymentHistory extends IdBaseEntity{

    @Column(length = 1000, nullable = false)
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

    /*Nje user ka shume histori punesimi , dhe 1 histori punesimi i perket 1 user
    * pra marredhenia eshte 1 me shume */
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
}
