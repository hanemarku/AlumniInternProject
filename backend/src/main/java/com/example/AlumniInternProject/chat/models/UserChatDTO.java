package com.example.AlumniInternProject.chat.models;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChatDTO extends IdBaseEntity {
    private String firstname;
    private String lastname;
    private String email;
    private String profilePicUrl;
}
