package com.example.AlumniInternProject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID post_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "like_id")
    private List<Like> likes;

    @OneToMany(mappedBy = "comment_id")
    private List<Comment> comments;

    private String content;
    private LocalDateTime postCreation;
    private String profilePicUrl;

}
