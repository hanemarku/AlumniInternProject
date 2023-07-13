package com.example.AlumniInternProject.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentDto {
    private UUID userComments;
    private String commentContent;
    private UUID postComments;
}
