package com.example.AlumniInternProject.Verfication;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import com.example.AlumniInternProject.user.DTOs.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    VerificationToken findByVerificationType(String verificationType);
    void deleteByUser(User user);
    //remove all tokens of a user
    void deleteAllByUser(User user);


}
