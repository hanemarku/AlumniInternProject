package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.MembershipRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class UserEvents extends IdBaseEntity {
   // @Column(length = 32, columnDefinition = "varchar(32)")
   // @Enumerated(value = EnumType.STRING)
    @Enumerated(value = EnumType.ORDINAL)
   private MembershipRole membershipRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;


    public UserEvents(MembershipRole membershipRole,
                      User user,
                      Events event) {
        this.membershipRole = membershipRole;
        this.user = user;
        this.event = event;
    }

/*
*     public UserEvents(MembershipRole membershipRole,
                      UUID userId,
                      UUID eventId) {
        this.membershipRole = membershipRole;
        this.getUser().setId(userId);
        this.getEvent().getId();
    }*/
}
