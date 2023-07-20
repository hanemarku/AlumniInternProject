package com.example.AlumniInternProject.user.security;

import com.example.AlumniInternProject.user.filter.JWTTokenGeneratorFilter;
import com.example.AlumniInternProject.user.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public JWTTokenValidatorFilter jwtTokenValidatorFilter(){
        return new JWTTokenValidatorFilter();
    }

    @Bean
    public JWTTokenGeneratorFilter jwtTokenGeneratorFilter(){
        return new JWTTokenGeneratorFilter();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new AlumniUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        //permit all requests
                        .anyRequest().permitAll()
//                        .requestMatchers(matchers("/homepage")).authenticated()
//                        .requestMatchers(matchers("/homepage3")).permitAll()
//                        .requestMatchers(matchers("/api/v1/**")).permitAll()
                )

                .formLogin(login -> login
                        .usernameParameter("email")
                        .defaultSuccessUrl("/homepage2")
                        .permitAll()
                )
                .httpBasic(withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(jwtTokenGeneratorFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    private RequestMatcher matchers(String pattern) {
        return new AntPathRequestMatcher(pattern);
    }
}
