package com.example.AlumniInternProject.Events.dto;

import com.example.AlumniInternProject.entity.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private String Name;
    private  String Topic;
    private String Description;
    private LocalDateTime Date;
    private int maxParticipants;
    private String imgUrl;
    private Set<City> cities = new HashSet<>();
    private UUID createdBy;

}
