package com.example.AlumniInternProject.user.listeners;

import com.example.AlumniInternProject.user.LoginAttemptService;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginAttemptService loginAttemptService;

    @Autowired
    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof ALumniUserDetails) {
            ALumniUserDetails user = (ALumniUserDetails) principal;
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
        // Trigger InteractiveAuthenticationSuccessEvent manually
//        InteractiveAuthenticationSuccessEvent interactiveAuthEvent =
//                new InteractiveAuthenticationSuccessEvent(authentication, getClass());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        onInteractiveAuthenticationSuccess(request, response, authentication);
    }


}
