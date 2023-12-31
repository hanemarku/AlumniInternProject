package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    @Column(length = 1500, nullable = true)
    private String createdBy;
    /*One event can have many specifics.
    * Such as date , city that is held.
    * So we establish the relationship one to many*/
    @OneToMany(mappedBy = "events",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EventSpecifics> eventSpecifics;

    public Events(String name,
                  String topic,
                  String description,
                  int maxParticipants,
                  String imgUrl,
                  Set<EventSpecifics> eventSpecifics,
                  String createdBy){
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.imgUrl = imgUrl;
        this.createdBy = createdBy;
    }
}
