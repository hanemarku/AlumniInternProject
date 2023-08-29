package com.example.AlumniInternProject.chat.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class MessageDTO  {

    private UUID senderId;
    private String message;
    private Date time;
    private ChatDTO chat;
    private String timejs;
    private String chatId;

}
