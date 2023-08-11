package com.example.AlumniInternProject.Verfication;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import com.example.AlumniInternProject.entity.VerificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;


    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken findByVerificationType(String verificationType) {
        return verificationTokenRepository.findByVerificationType(verificationType);
    }

    @Transactional
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }


    public void save(User user, String token, VerificationType verificationType) {
        VerificationToken verificationToken = new VerificationToken(user, token, verificationType);
        verificationToken.setExpirationDate(calculateExpirationDate(24*60));
        verificationTokenRepository.save(verificationToken);
    }

    private Timestamp calculateExpirationDate(int expirationTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Timestamp(calendar.getTime().getTime());
    }

    //make a method to delete the token after the user has verified their email
    public void deleteByUser(User user) {
        verificationTokenRepository.deleteByUser(user);
    }
    //make a method to remove all tokens of a user
    public void deleteAllByUser(User user) {
        verificationTokenRepository.deleteByUser(user);
    }

}
