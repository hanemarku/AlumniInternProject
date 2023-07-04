package com.example.AlumniInternProject.Events.dto;

import com.example.AlumniInternProject.entity.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate Date;
    private boolean LimitedMembers;
    private int maxParticipants;
    private String imgUrl;
    private Set<City> cities = new HashSet<>();

}
