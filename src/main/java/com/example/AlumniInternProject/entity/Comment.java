package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userComments;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postComments;

    private String content;
}
