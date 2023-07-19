package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.UserEvents;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<UserEvents> userEvents;
}
