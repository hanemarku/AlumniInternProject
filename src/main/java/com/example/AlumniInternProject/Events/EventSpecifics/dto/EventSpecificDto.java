package com.example.AlumniInternProject.Events.EventSpecifics.dto;

import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.UserEvents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventSpecificDto {
    private LocalDateTime date;
    private Events events;
    private City city;

    public EventSpecificDto(LocalDateTime date,
                          UUID eventId,
                          UUID cityId) {
        this.date = date;
        this.events.setId(eventId);
        this.city.setId(cityId);
    }
}
