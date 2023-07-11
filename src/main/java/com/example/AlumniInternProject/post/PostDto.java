package com.example.AlumniInternProject.post;


import com.example.AlumniInternProject.like.LikeDto;
import com.example.AlumniInternProject.user.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Integer id;
    private UserDTO author;
    private LikeDto likedBy;
    private String title;
    private String content;
    private String tag;
    private String keyword;
    private LocalDateTime dateOfPost;

}
