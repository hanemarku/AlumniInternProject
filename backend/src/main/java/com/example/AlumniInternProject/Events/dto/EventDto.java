package com.example.AlumniInternProject.Events.dto;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private String Name;
    private  String Topic;
    private String Description;
    private int maxParticipants;
    private String imgUrl;
    private Set<EventSpecifics> eventSpecifics = new HashSet<>();
    private String createdBy;
}
