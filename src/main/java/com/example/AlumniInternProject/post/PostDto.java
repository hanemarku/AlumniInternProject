package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.like.Like;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostDto {

    private User user;
    private Like like;
    private String content;
    private Timestamp dateOfPost;
    private Integer likesCount;
    private Integer commentsCount;
}
