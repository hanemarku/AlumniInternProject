package com.example.AlumniInternProject.chat.models;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatDTO extends IdBaseEntity {
    private UUID admin;
    private ChatType type;
    private String name;
    private Set<UserChatDTO> users;
}
