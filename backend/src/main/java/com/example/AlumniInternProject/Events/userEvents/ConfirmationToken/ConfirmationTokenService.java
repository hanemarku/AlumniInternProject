package com.example.AlumniInternProject.Events.userEvents.ConfirmationToken;

import com.example.AlumniInternProject.Events.userEvents.UserEventsService;
import com.example.AlumniInternProject.entity.UserEvents;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
