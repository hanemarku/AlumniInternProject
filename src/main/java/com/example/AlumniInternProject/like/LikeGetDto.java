package com.example.AlumniInternProject.like;

import jakarta.persistence.Id;

import java.util.UUID;

public class LikeGetDto extends LikeDto{
    @Id
    private UUID id = UUID.randomUUID();
}
