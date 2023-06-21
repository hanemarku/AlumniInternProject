package com.example.AlumniInternProject.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Role extends IdBaseEntity{
    private String name;
    private String description;

}
