package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.UserEvents;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSpecifics extends IdBaseEntity {
    /*When adding We should have unique date and city
    * combination for the same event id*/
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDate date;
    /*Many events have only one specification*/
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events events;
    /*Many cities have only one specification*/
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "eventSpecifics")
    @JsonIgnore
    private Set<UserEvents> userEvents;

 public EventSpecifics(LocalDate date,
                          UUID eventId,
                          UUID cityId) {
        this.date = date;
    }
    public EventSpecifics(LocalDate date,
                          Events events,
                          City city) {
     this.date = date;
     this.city = city;
     this.events = events;
    }
}
