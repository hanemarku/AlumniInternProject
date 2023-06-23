package com.example.AlumniInternProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends IdBaseEntity{
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 150, nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> users;

}
