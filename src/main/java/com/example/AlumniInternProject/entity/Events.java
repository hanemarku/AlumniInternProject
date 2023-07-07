package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

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

    private String imgUrl;

    private int maxParticipants;

    private UUID createdBy;

    /*One event may have many members.
    * One member may be part of many events.
    * We create a new table for this many to many relationship
    *
    *     @OneToMany(mappedBy = "event")
    private List<UserEvents> userEvents;
    * */


    /*One event can have many specifics.
    * Such as date , city that is held.
    * So we establish the relationship one to many*/
    @OneToMany(mappedBy = "events")
    private List<EventSpecifics> eventSpecifics;


    public Events(String name,
                  String topic,
                  String description,
                  String imgUrl,
                  int maxParticipants, Set<EventSpecifics> eventSpecifics){
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.imgUrl = imgUrl;
    }
}
