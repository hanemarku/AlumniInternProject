package com.example.AlumniInternProject.Events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
