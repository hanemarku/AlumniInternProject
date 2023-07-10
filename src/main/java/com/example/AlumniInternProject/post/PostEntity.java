package com.example.AlumniInternProject.post;


import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.like.LikeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany
    @JoinColumn(name = "like_id")
    private List<LikeEntity> likedBy;

    private String title;
    private String content;
    private String tag;
    private String keyword;
    private LocalDateTime dateOfPost;
}
