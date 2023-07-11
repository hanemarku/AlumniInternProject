package com.example.AlumniInternProject.like;


import com.example.AlumniInternProject.entity.Comment;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikeDto {
    private Integer id;
    private User user;
    private Post post;
    private Comment comment;
    private LocalDateTime dateOfLike;
}
