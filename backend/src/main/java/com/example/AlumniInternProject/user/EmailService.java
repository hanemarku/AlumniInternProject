package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.Verfication.VerificationTokenService;
import com.example.AlumniInternProject.admin.settings.SettingRepository;
import com.example.AlumniInternProject.entity.Setting;
import com.example.AlumniInternProject.entity.SettingCategory;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final SettingRepository settingRepository;

    public void sendVerificationEmail(User user) {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);
        if (verificationToken != null) {
            String token = verificationToken.getToken();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String emailSubject = getSettingContentFromKey("CUSTOMER_VERIFY_SUBJECT", SettingCategory.MAIL_TEMPLATES);
            String emailContent = getSettingContentFromKey("CUSTOMER_VERIFY_CONTENT", SettingCategory.MAIL_TEMPLATES);
            emailContent = emailContent.replace("[token]", token);
            System.out.println(emailContent);

            emailContent = emailContent.replace("[name]", user.getFirstname() + " " + user.getLastname());

            try {
                helper.setTo(user.getEmail());
                helper.setSubject(emailSubject);
                helper.setText(emailContent, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSettingContentFromKey(String key, SettingCategory category) {
        Setting emailContentSetting = settingRepository.findByCategory(category).stream()
                .filter(setting -> setting.getKey().equals(key)) // Fix the closing parenthesis here
                .findFirst()
                .orElse(null);
        return emailContentSetting != null ? emailContentSetting.getValue() : "";
    }

}
