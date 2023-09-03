package com.example.AlumniInternProject.chat.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatGroupRequest {
    public UUID id;
    public String name;
}
