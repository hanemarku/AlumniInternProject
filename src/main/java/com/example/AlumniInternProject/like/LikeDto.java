package com.example.AlumniInternProject.like;


import com.example.AlumniInternProject.comment.Comment;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LikeDto {
    private User user;
    private Post post;
    private Comment comment;
    private Date dateOfLike;
}
