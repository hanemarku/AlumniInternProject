package com.example.AlumniInternProject.recommendation;

import com.example.AlumniInternProject.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {
}
