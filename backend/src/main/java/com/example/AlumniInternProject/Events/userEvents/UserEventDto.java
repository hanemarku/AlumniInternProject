package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.entity.MembershipRole;
import com.example.AlumniInternProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDto {
    public User user;
    public EventSpecifics eventSpecifics;
    public MembershipRole membershipRole;
    public Status status;
}
