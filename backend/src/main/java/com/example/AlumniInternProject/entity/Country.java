package com.example.AlumniInternProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Countries")
public class Country extends IdBaseEntity {
    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 5)
    private String code;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Set<City> cities;

    public Country(String name, String code) {
        super();
        this.name = name;
        this.code = code;
    }

    public Country(UUID id, String name, String code) {
        super(id);
        this.name = name;
        this.code = code;
    }

    public Country(UUID id){
        super(id);
    }

    public Country() {

    }
}
