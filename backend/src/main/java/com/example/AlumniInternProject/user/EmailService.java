package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.constants.EmailConstants;
import com.sun.mail.smtp.SMTPTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.example.AlumniInternProject.constants.EmailConstants.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("alumniproject4@gmail.com")
    private final String sender = "alumniproject4@gmail.com";

    @Autowired
    private final TemplateEngine templateEngine;

    public void sendNewPasswordEmail(String firstname, String password, String email) {
        SimpleMailMessage message = createEmail(firstname, password, email);
        mailSender.send(message);
    }

    private SimpleMailMessage createEmail(String firstname, String password, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello " + firstname + ",\n\nYour new password is: " + password + "\n\nThe Alumni Team");
        return message;
    }



//    public void sendNewPasswordEmail(String firstname, String password, String email) throws MessagingException {
//        Message message = createEmail(firstname, password, email);
//        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
//        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
//        smtpTransport.sendMessage(message, message.getAllRecipients());
//        smtpTransport.close();
//    }
//
//    private Message createEmail(String firstname, String password, String email) throws MessagingException {
//        Message message = new MimeMessage(getEmailSession());
//        message.setFrom(new InternetAddress(FROM_EMAIL));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
//        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_EMAIL, false));
//        message.setSubject(EMAIL_SUBJECT);
//        message.setText("Hello " + firstname + ",\n\nYour new password is: " + password + "\n\nThe Alumni Team");
//        message.setSentDate(new java.util.Date());
//        message.saveChanges();
//        return message;
//    }
//
//    private Session getEmailSession() {
//        Properties properties = System.getProperties();
//        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
//        properties.put(SMTP_AUTH, true);
//        properties.put(SMTP_PORT, DEFAULT_PORT);
//        properties.put(SMTP_STARTTLS_ENABLE, true);
//        properties.put(SMTP_STARTTLS_REQUIRED, true);
//        return Session.getInstance(properties, null);
//    }
}
