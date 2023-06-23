package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Entity
@Table
@Getter
@Setter
public class Events extends IdBaseEntity {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String topic;
    @Column(length = 1500, nullable = false)
    private String description;
    private LocalDate date;
    private boolean limitedMembers;
    private String imgUrl;
    private UUID createdBy;

    /* 1 event ka shume user ,
    * por 1 user mund te jete pjese e shume eventeve.
    * Mqs marredhenie shume me shume ath krijojme lidhjen
    * 1 me shume me tabelen UserEvents*/

    /* 1 event ka shume lokacione ,
    por ne 1 lokacion ka gjithashtu shume evente  */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cityEvents",
            joinColumns= @JoinColumn(name = "city_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<City> cities = new HashSet<>();
}
