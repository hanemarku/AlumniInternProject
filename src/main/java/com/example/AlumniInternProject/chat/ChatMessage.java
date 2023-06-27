package com.example.AlumniInternProject.chat;


import com.example.AlumniInternProject.entity.IdBaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends IdBaseEntity {
    private MassageType type;
    private String content;
    private String sender;

    public enum MassageType  {
        CHAT,
        JOIN,
        LEAVE
    }

}
