package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "Cities")
public class City extends IdBaseEntity {
    @Column(nullable = false, length = 45)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    /*One city can have many specifics.
     * Such as date and event_id that is held.
     * So we establish the relationship one to many*/
    @OneToMany(mappedBy = "city")
    private List<EventSpecifics> eventSpecifics;
    public City() {
    }
}
