package com.example.AlumniInternProject.chat.models;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "chats")
public class Chat extends IdBaseEntity {

    private UUID admin;

    @Column(nullable = false)
    private ChatType type;

    @Column(nullable = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "chat")
    @JsonManagedReference
    private Set<Message> messages = new HashSet<Message>();
}
