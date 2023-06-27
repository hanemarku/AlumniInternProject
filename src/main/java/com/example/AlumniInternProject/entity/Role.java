package com.example.AlumniInternProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends IdBaseEntity{
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 150, nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> users;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
