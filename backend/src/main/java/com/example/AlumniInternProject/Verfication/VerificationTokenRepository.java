package com.example.AlumniInternProject.Verfication;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import com.example.AlumniInternProject.entity.VerificationType;
import com.example.AlumniInternProject.user.DTOs.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    VerificationToken findByVerificationType(String verificationType);
    void deleteByUser(User user);
    void deleteByUserAndVerificationType(User user, VerificationType verificationType);
    VerificationToken findByUserIdAndVerificationType(UUID userId, VerificationType verificationType);

}
