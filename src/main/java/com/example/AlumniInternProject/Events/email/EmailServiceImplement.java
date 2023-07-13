package com.example.AlumniInternProject.Events.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class EmailServiceImplement implements EmailService{
    @Autowired
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public void sendSimpleMail(Email details) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(details.getRecipient());
        message.setSubject(details.getSubject());
        message.setText(details.getMsgBody());

        emailSender.send(message);
    }
}
