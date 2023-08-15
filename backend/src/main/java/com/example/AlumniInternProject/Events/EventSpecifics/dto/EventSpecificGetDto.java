package com.example.AlumniInternProject.Events.EventSpecifics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class EventSpecificGetDto extends EventSpecificDto{
    private UUID id;
}
