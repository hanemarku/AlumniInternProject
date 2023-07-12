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
@Table(name = "comments")
public class Comment extends IdBaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userComments;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postComments;

    private String content;
}
