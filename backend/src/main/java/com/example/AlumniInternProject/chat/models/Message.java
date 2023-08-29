package com.example.AlumniInternProject.chat.models;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "messages")
public class Message extends IdBaseEntity {

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonBackReference
    private Chat chat;

    @Transient
    private String timejs;


}
