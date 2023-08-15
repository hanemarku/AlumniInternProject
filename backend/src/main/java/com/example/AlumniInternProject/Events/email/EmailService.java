package com.example.AlumniInternProject.Events.email;

import org.springframework.ui.Model;

public interface EmailService {
    String sendSimpleMail(String email ,String token);

}
