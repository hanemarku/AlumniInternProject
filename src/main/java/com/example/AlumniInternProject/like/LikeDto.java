package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.comment.CommentDto;
import com.example.AlumniInternProject.post.PostDto;
import com.example.AlumniInternProject.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikeDto {
    private Integer id;
    private UserDTO user;
    private PostDto post;
    private CommentDto comment;
    private LocalDateTime dateOfLike;
}
