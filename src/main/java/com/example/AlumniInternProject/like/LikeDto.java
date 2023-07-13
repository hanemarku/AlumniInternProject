package com.example.AlumniInternProject.like;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LikeDto {
    private UUID userLikes;
    private UUID postLikes;
}
