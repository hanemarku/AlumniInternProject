package com.example.AlumniInternProject.user.filter2;

import com.example.AlumniInternProject.user.utility.JWTTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.example.AlumniInternProject.constants.SecurityConstants.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JWTTokenProvider jwtTokenProvider;
    public JwtAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        }else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)){
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);
            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
