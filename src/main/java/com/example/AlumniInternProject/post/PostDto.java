package com.example.AlumniInternProject.post;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostDto {

    private UUID author;
    private List<UUID> like;
    private List<UUID> comment;
    private String content;
    private LocalDateTime postCreation;
    private String profilePicUrl;

}
