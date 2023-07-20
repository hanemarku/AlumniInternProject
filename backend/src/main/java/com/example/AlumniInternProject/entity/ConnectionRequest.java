package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "connection_requests")
public class ConnectionRequest extends IdBaseEntity{
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User requestee;

    @Enumerated(EnumType.STRING)
    private ConnectionRequestStatus status;


    public ConnectionRequest() {

    }

    public ConnectionRequest(User sender, User receiver) {
        this.requester = sender;
        this.requestee = receiver;
    }
}