package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.comment.Comment;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like extends IdBaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private Timestamp dateOfLike;
}
