package com.example.AlumniInternProject.Verfication;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import com.example.AlumniInternProject.entity.VerificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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

    public void deleteByUserAndVerificationType(User user, VerificationType verificationType) {
        verificationTokenRepository.deleteByUserAndVerificationType(user, verificationType);
    }

    public void removeTokenByUserAndType(UUID userId, VerificationType verificationType) {

        VerificationToken tokensToRemove = verificationTokenRepository.findByUserIdAndVerificationType(userId, verificationType);
        verificationTokenRepository.delete(tokensToRemove);
    }

    public void removeToken(Long token) {
        verificationTokenRepository.deleteById(token);
    }

    public void deleteByUser(User user) {
        verificationTokenRepository.deleteByUser(user);
    }
    public void deleteAllByUser(User user) {
        verificationTokenRepository.deleteByUser(user);
    }

}
