package com.example.AlumniInternProject.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RecommendationDto {
    private UUID recommender;
    private UUID recommendedUser;
    private String comment;
    private LocalDateTime timestamp;

}
