package com.example.AlumniInternProject.comment;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.post.PostEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CommentDto {

    private UUID id;

    private User user;
    private PostEntity post;
    private String content;
    private Date dateOfCreation;
}
