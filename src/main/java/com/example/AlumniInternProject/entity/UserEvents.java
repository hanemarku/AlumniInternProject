package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
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
public class UserEvents extends IdBaseEntity {
   // @Column(length = 32, columnDefinition = "varchar(32)")
   // @Enumerated(value = EnumType.STRING)
    @Enumerated(value = EnumType.ORDINAL)
   private MembershipRole membershipRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "eventSpecifics")
    private EventSpecifics eventSpecifics;

    public UserEvents(MembershipRole membershipRole,
                      User user,
                      EventSpecifics eventSpecifics) {
        this.membershipRole = membershipRole;
        this.user = user;
        this.eventSpecifics = eventSpecifics;
    }
}
