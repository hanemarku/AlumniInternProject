package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    private String createdBy;
    /*One event can have many specifics.
    * Such as date , city that is held.
    * So we establish the relationship one to many*/
    @OneToMany(mappedBy = "events")
    @JsonIgnore
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
