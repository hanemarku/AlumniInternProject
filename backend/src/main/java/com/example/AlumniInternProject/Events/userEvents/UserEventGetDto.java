package com.example.AlumniInternProject.Events.userEvents;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserEventGetDto extends UserEventDto{
    public UUID id;
}
