package com.example.AlumniInternProject.post;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostGetDto extends PostDto{
    private UUID id;
}
