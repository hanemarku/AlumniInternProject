package com.example.AlumniInternProject.chat.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserToGroupDTO {
    private UUID groupId;
    private UUID userId;
}
