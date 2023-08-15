package com.example.AlumniInternProject.user.security;

import com.example.AlumniInternProject.user.filter.JwtAccessDeniedHandler;
import com.example.AlumniInternProject.user.filter.JwtAuthenticationEntryPoint;
import com.example.AlumniInternProject.user.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
        private JwtAuthorizationFilter jwtAuthorizationFilter;

        @Bean
        public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
            return new JwtAuthenticationEntryPoint();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new AlumniUserDetailsService();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    public void setJwtAccessDeniedHandler(JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Autowired
        public void setJwtAuthorizationFilter(JwtAuthorizationFilter jwtAuthorizationFilter) {
            this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf((csrf) -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(requests -> requests
                                    .anyRequest().permitAll()
//                            .requestMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
//                            .anyRequest().authenticated()
                    )
                    .exceptionHandling(exception -> exception
                            .accessDeniedHandler(jwtAccessDeniedHandler)
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                    )
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin(login -> login
                            .usernameParameter("email")
                            .defaultSuccessUrl("/homepage2")
                            .permitAll()
                    )
                    .httpBasic(withDefaults())
                    .authenticationProvider(authenticationProvider());
            return http.build();
        }

        private RequestMatcher matchers(String pattern) {
            return new AntPathRequestMatcher(pattern);
        }

    }


