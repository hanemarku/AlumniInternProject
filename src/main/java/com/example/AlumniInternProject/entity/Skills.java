package com.example.AlumniInternProject.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Skills extends IdBaseEntity{
    private String name;
}
