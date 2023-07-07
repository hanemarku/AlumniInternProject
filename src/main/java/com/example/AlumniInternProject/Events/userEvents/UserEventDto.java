package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.MembershipRole;
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
    public UUID eventId;
//    public MembershipRole membershipRole;
}
