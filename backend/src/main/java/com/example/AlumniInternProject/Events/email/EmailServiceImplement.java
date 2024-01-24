package com.example.AlumniInternProject.Events.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImplement implements EmailService{

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("alumniproject4@gmail.com")
    private final String sender = "alumniproject4@gmail.com";
    private final String SUBJ_TEST = "Confrim your reservation";

    @Override
    @Async
    public String sendSimpleMail(String email, String token) {
      try{
          MimeMessage message = emailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true);

          helper.setFrom(sender);
          helper.setTo(email);
          helper.setSubject(SUBJ_TEST);

          Context context = new Context();
          context.setVariable("LINK", token);

          String htmlContent = templateEngine.process("confirmationEmail.html", context);
          helper.setText(htmlContent, true);
          emailSender.send(message);
          return "Email sent succesfully";
      }catch (Exception e){
          return "Email could not be sent!";
      }
    }
}
