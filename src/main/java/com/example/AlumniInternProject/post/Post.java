package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.like.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends IdBaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "like_id")
    private Like like;

    private String content;
    private Date dateOfPost;
    private Integer likesCount;
    private Integer commentsCount;
    private String keyword;
    private String category;
    private String tag;


}
