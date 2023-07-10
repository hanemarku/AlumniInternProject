package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "reviews")
public class Recommendation extends IdBaseEntity {

    private LocalDateTime timestamp;

    @Column(length = 1000, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "recommender_id")
    private User recommender;

    @ManyToOne
    @JoinColumn(name = "recommended_user_id")
    private User recommendedUser;

    public Recommendation(User recommender, User recommendedUser, String comment, LocalDateTime timestamp) {
        this.recommender = recommender;
        this.recommendedUser = recommendedUser;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    //git delete
}