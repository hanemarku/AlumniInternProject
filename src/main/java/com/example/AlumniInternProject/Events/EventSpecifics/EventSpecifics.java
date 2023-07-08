package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.UserEvents;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime date;
    /*Many events have only one specification*/
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events events;
    /*Many cities have only one specification*/
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "eventSpecifics")
    private Set<UserEvents> userEvents;

 public EventSpecifics(LocalDateTime date,
                          UUID eventId,
                          UUID cityId) {
        this.date = date;
    }

    public EventSpecifics(LocalDateTime date,
                          Events events,
                          City city) {
     this.date = date;
     this.city = city;
     this.events = events;
    }
}
