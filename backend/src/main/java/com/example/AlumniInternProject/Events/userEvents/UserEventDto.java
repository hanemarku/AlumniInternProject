package com.example.AlumniInternProject.Events.userEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDto {
    public UUID userId;
    public UUID eventSpecificsId;
//    public MembershipRole membershipRole;
}