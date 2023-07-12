package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "likes")
public class Like extends IdBaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userLikes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postLikes;
}
