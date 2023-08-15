package com.example.AlumniInternProject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "posts")
public class Post extends IdBaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "postLikes")
    private List<Like> likes;

    @OneToMany(mappedBy = "postComments")
    private List<Comment> comments;

    private String content;
    private LocalDateTime postCreation;
    private String profilePicUrl;

}
