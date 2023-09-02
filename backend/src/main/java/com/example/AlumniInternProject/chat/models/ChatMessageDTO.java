package com.example.AlumniInternProject.chat.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ChatMessageDTO {
    private UUID senderId;
    private String message;
    private Date time;
}
