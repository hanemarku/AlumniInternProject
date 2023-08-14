package com.example.AlumniInternProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String token;
    private Timestamp expirationDate;

    @Enumerated(EnumType.STRING)
    private VerificationType verificationType;

    public VerificationToken() {
    }

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public VerificationToken(User user, String token, VerificationType verificationType) {
        this.user = user;
        this.token = token;
        this.verificationType = verificationType;
    }

    public VerificationToken(int id, User user, String token, Timestamp expirationDate, VerificationType verificationType) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.expirationDate = expirationDate;
        this.verificationType = verificationType;
    }

    public VerificationToken(User user, String token, VerificationType verificationType, Timestamp expirationDate) {
        this.user = user;
        this.token = token;
        this.verificationType = verificationType;
        this.expirationDate = expirationDate;
    }
}
