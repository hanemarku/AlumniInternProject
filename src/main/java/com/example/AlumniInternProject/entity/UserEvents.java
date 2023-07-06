package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.MembershipRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvents extends IdBaseEntity {
    @Column(length = 32, columnDefinition = "varchar(32)")
    @Enumerated(value = EnumType.STRING)
   private MembershipRole membershipRole;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

    public UserEvents(MembershipRole membershipRole,
                      UUID userId,
                      UUID eventId) {
        this.membershipRole = membershipRole;
        this.getUser().getId();
        this.getEvent().getId();
    }
}
