package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.like.Like;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
public class PostDto {

    private User user;
    private Like like;
    private String content;
    private Date dateOfPost;
    private Integer likesCount;
    private Integer commentsCount;
    private String keyword;
    private String category;
    private String tag;
    private Set<User> sharedByUsers;
    private Set<User> sharedWithUsers;
}
