package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Events extends IdBaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String topic;

    @Column(length = 1500, nullable = false)
    private String description;

    private LocalDateTime date;

    private String imgUrl;

    private int maxParticipants;

    private UUID createdBy;

    /* 1 event ka shume user ,
    * por 1 user mund te jete pjese e shume eventeve.
    * Mqs marredhenie shume me shume ath krijojme lidhjen
    * 1 me shume me tabelen UserEvents*/
    @OneToMany(mappedBy = "event")
    private List<UserEvents> userEvents;

    /* 1 event ka shume lokacione ,
    por ne 1 lokacion ka gjithashtu shume evente  */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cityEvents",
            joinColumns= @JoinColumn(name = "city_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<City> cities = new HashSet<>();

    public Events(String name,
                  String topic,
                  String description,
                  LocalDateTime date,
                  String imgUrl,
                  int maxParticipants,
                  Set<City> cities){
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.date = date;
        this.maxParticipants = maxParticipants;
        this.imgUrl = imgUrl;
        this.cities = cities;
    }
}
