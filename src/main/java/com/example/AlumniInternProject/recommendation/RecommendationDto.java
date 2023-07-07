package com.example.AlumniInternProject.recommendation;

import com.example.AlumniInternProject.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecommendationDto {
    private User recommender;
    private User recommendedUser;
    private String comment;
    private LocalDateTime timestamp;

}
