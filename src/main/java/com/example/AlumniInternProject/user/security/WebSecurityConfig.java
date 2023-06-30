package com.example.AlumniInternProject.user.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

           http.authorizeHttpRequests(requests -> requests
                   .requestMatchers("/api/v1/**").permitAll()
                           .anyRequest().permitAll())
                    .formLogin(Customizer.withDefaults())
                    .httpBasic(Customizer.withDefaults())
                   .csrf((csrf) -> {
                       csrf
                               .ignoringRequestMatchers(matchers("/api/v1/**"))
                               .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                   });
           return http.build();


    }

    // create the above method to permit all requests
    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    private RequestMatcher matchers(String pattern) {
        return new AntPathRequestMatcher(pattern);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/homepage").authenticated() // Requires authentication for /homepage
//                .antMatchers("/homepage2", "/countries/save").permitAll() // Permits access without authentication for /homepage2 and /countries/save
//                .anyRequest().authenticated() // Requires authentication for any other request
//                .and()
//                .formLogin() // Enables form-based authentication
//                .and()
//                .httpBasic(); // Enables Basic Authentication
//    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
