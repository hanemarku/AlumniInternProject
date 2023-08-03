package com.example.AlumniInternProject.user.security;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.LoginAttemptService;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.AlumniInternProject.constants.UserImplConstants.USER_FOUND_BY_EMAIL;
import static com.example.AlumniInternProject.constants.UserImplConstants.USER_NOT_FOUND_BY_EMAIL;

@Service
public class AlumniUserDetailsService implements UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;



    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            LOGGER.error("User not found in the database" + email);
            try {
                throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            validateLoginAttempt(user);
            userRepository.save(user);
            ALumniUserDetails userDetails = new ALumniUserDetails(user);
            LOGGER.info(USER_FOUND_BY_EMAIL + email);
            return userDetails;
        }
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()){
            if(loginAttemptService.hasExceededMaxAttempts(user.getEmail())){
                user.setNotLocked(false);
            }else{
                user.setNotLocked(true);
            }
        }else{
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }



}
