package com.example.AlumniInternProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority extends IdBaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    private String name;

    public Authority(String name) {
        this.name = name;
    }
}
