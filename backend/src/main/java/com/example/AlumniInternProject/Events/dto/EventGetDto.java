package com.example.AlumniInternProject.Events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class EventGetDto extends  EventDto {
    private UUID id;
}
