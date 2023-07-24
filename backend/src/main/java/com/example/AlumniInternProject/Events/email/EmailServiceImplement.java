package com.example.AlumniInternProject.Events.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImplement implements EmailService{
    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private final TemplateEngine templateEngine;
    @Value("alumniproject4@gmail.com")
    private final String sender = "alumniproject4@gmail.com";

    private final String MSG_BODY_TEST = "this is the body String";
    @Override
    @Async
    public String sendSimpleMail(Email details) {
      try{
          SimpleMailMessage message = new SimpleMailMessage();
          Context context = new Context();
          message.setFrom(sender);
          message.setTo(details.getRecipient());
          message.setSubject(details.getSubject());
          message.setText(MSG_BODY_TEST);
          context.setVariable("message",message);
          emailSender.send(message);
          return templateEngine.process("confirmationEmail.html", context);
      }catch (Exception e){
          return "Email could not be sent!";
      }
    }
}
