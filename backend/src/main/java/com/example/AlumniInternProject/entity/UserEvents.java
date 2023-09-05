package com.example.AlumniInternProject.entity;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.userEvents.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvents extends IdBaseEntity {

    @Enumerated(value = EnumType.ORDINAL)
   private MembershipRole membershipRole;
    /*Here will be saved all the users that are registered for the event
    * and the status of the participation there*/
    @Column(length = 32, columnDefinition = "varchar(32) default 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eventSpecifics")
    private EventSpecifics eventSpecifics;

    public UserEvents(MembershipRole membershipRole,
                      User user,
                      EventSpecifics eventSpecifics) {
        this.membershipRole = membershipRole;
        this.user = user;
        this.eventSpecifics = eventSpecifics;
    }
    public UserEvents(MembershipRole membershipRole,
                      User user,
                      EventSpecifics eventSpecifics,
                      Status status) {
        this.membershipRole = membershipRole;
        this.user = user;
        this.eventSpecifics = eventSpecifics;
        this.status = status;
    }
}
