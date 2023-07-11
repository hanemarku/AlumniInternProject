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
@Table(name = "Likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID like_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userLikes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postLikes;
}
