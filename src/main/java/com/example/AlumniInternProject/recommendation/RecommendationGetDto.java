package com.example.AlumniInternProject.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecommendationGetDto extends RecommendationDto{
    private UUID id;
}
