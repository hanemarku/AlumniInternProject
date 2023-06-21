package com.example.AlumniInternProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "interests")

public class Interest extends IdBaseEntity{
    @Column(length = 45, nullable = false)
    private String name;

}
