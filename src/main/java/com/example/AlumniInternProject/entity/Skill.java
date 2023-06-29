package com.example.AlumniInternProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "skills")
public class Skill extends IdBaseEntity{
    @Column(length = 45, nullable = false)
    private String name;
    private int count;

    public Skill(String name, int count){
        this.name = name;
        this.count = count;
    }
}
