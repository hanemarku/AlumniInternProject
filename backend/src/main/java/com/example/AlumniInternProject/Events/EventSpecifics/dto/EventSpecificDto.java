package com.example.AlumniInternProject.Events.EventSpecifics.dto;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.entity.City;
import com.example.AlumniInternProject.entity.Events;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventSpecificDto extends EventSpecifics {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Events events;
    private City city;

    public EventSpecificDto(LocalDate date,
                          UUID eventId,
                          UUID cityId) {
        this.date = date;
        this.events.setId(eventId);
        this.city.setId(cityId);
    }
}
