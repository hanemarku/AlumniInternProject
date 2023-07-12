package com.example.AlumniInternProject.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentGetDto extends CommentDto{
    private UUID comment_id;
}
