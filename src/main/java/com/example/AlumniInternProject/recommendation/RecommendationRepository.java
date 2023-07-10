package com.example.AlumniInternProject.recommendation;

import com.example.AlumniInternProject.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {

    @Query("SELECT r FROM Recommendation r WHERE r.recommender.id = :recommenderId")
    Optional<Recommendation> findByRecommenderId(@Param("recommenderId") UUID recommenderId);
    @Query("SELECT r FROM Recommendation r WHERE r.recommendedUser.id = :recommendedUserId")
    Optional<Recommendation> findByRecommendedUserId(@Param("recommendedUserId") UUID recommendedUserId);
}
