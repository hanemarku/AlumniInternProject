package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.admin.settings.country.CountryRepository;
import com.example.AlumniInternProject.entity.*;
import com.example.AlumniInternProject.enumerations.Role;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.UserDTO;
import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

import java.util.stream.Collectors;

import static com.example.AlumniInternProject.constants.UserImplConstants.*;

@Service
@RequiredArgsConstructor
@Transactional

public class UserServiceImpl implements UserService{
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    private final CountryRepository countryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
//    private final EmailService emailService;
//    private LoginAttemptService loginAttemptService;

//    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserGetDto save(UserDTO userDto) throws UserNotFoundException, EmailExistException {
        validateNewEmail(StringUtils.EMPTY, userDto.getEmail());
        String password = encodePassword(userDto.getPassword());

        Set<Authority> authorities = Role.ROLE_USER.getAuthorities().stream()
                .map(Authority::new)
                .filter(authority -> !authorityRepository.existsByName(authority.getName()))
                .collect(Collectors.toSet());


        authorities.forEach(authorityRepository::save);
        authorities = authorityRepository.findAll().stream().collect(Collectors.toSet());
        authorities = authorities.stream().filter(authority -> Role.ROLE_USER.getAuthorities().contains(authority.getName())).collect(Collectors.toSet());

        var user = new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getEmail(),
                true,
                userDto.getBirthday(),
                userDto.getProfilePicUrl(),
//                getTemporaryProfileImageUrl(userDto.getFirstname()),
                userDto.getPhoneNumber(),
                userDto.getCity(),
                userDto.getCountry(),
                password,
                userDto.getBio(),
                userDto.getSkills(),
                userDto.getInterests(),
                Role.ROLE_USER,
                userDto.getEmploymentHistories(),
                userDto.getEducationHistories(),
                userDto.getAuthorities()
//                true,
//                true
        );


        userDto.setAuthorities(authorities);
        user.setAuthorities(authorities);
        userDto.getEmploymentHistories().forEach(employmentDto -> employmentDto.setUser(user));
        userDto.getEducationHistories().forEach(educationDto -> educationDto.setUser(user));

        var savedUser = userRepository.save(user);
        LOGGER.info("New user was created with password: " + user.getPassword());
//        emailService.sendNewPasswordEmail(userDto.getFirstname(), userDto.getEmail(), password);
//        emailService.sendNewPasswordEmail(userDto.getFirstname(), password, userDto.getEmail());

        return map(savedUser);
    }


    private User validateNewEmail(String currentEmail, String newEmail) throws UserNotFoundException, EmailExistException {
        if(StringUtils.isNotBlank(currentEmail)) {
            User currentUser = findUserByEmail(currentEmail);
            if(currentUser == null) {
                throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + currentEmail);
            }
            User userByEmail1 = findUserByEmail(newEmail);
            if(userByEmail1 != null && !currentUser.getId().equals(userByEmail1.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        }else {
            User userByEmail = findUserByEmail(newEmail);
            if(userByEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            LOGGER.error("User not found by email: " + email);
            throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email);
        }else {
            userRepository.save(user);
            ALumniUserDetails userDetails = new ALumniUserDetails(user);
            LOGGER.info("Returning found user by email: " + email);
            return userDetails;
        }
    }


    @Transactional
    @Override
    public List<UserGetDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapForListing(user))
                .collect(Collectors.toList());
    }


    private UserGetDto map(User user) {
        var dto = new UserGetDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setBirthday(user.getBirthday());
        dto.setProfilePicUrl(user.getProfilePicUrl());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCity(user.getCity());
        dto.setCountry(user.getCountry());
        dto.setBio(user.getBio());
        dto.setBirthday(user.getBirthday());
        dto.setSkills(user.getSkills());
        dto.setInterests(user.getInterests());
        dto.setRole(user.getRole());
        dto.setAuthorities(user.getAuthorities());
        dto.setEducationHistories(user.getEducationHistories());
        dto.setEmploymentHistories(user.getEmploymentHistories());

        return dto;
    }

    private UserGetDto mapForListing(User user) {
        var dto = new UserGetDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setBirthday(user.getBirthday());
        dto.setProfilePicUrl(user.getProfilePicUrl());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCity(user.getCity());
        dto.setCountry(user.getCountry());
        dto.setBio(user.getBio());
        dto.setBirthday(user.getBirthday());
        dto.setSkills(user.getSkills());
        dto.setInterests(user.getInterests());
        dto.setRole(user.getRole());
        dto.setAuthorities(user.getAuthorities());
//        dto.setEducationHistories(user.getEducationHistories());
//        dto.setEmploymentHistories(user.getEmploymentHistories());
        return dto;
    }

    public List<Country> listAllCountries() {
        return (List<Country>) countryRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return null;
    }

    @Override
    public UserGetDto update(UUID id, UserDTO dto) throws UserNotFoundException {
        try {
            var user = userRepository.findById(id).get();
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setEmail(dto.getEmail());
            user.setEnabled(dto.isEnabled());
            user.setBirthday(dto.getBirthday());
            user.setProfilePicUrl(dto.getProfilePicUrl());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setCity(dto.getCity());
            user.setCountry(dto.getCountry());
            user.setBio(dto.getBio());
            user.setSkills(dto.getSkills());
            user.setInterests(dto.getInterests());
            user.setRole(dto.getRole());
            var savedUser = userRepository.save(user);
            return map(savedUser);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID + id);
        }
    }

    public User get(UUID id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID + id);
        }
    }

    @Override
    public void updateEnabledStatus(UUID id, boolean status) {
        userRepository.updateEnabledStatus(id, status);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return false;
        }
        return true;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws  UserNotFoundException {
//        User user = userRepository.findUserByEmail(email);
//        if (user == null) {
//            LOGGER.error("User not found in the database" + email);
//            throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email);
//        }else{
//            validateLoginAttempt(user);
//            userRepository.save(user);
//            ALumniUserDetails userDetails = new ALumniUserDetails(user);
//            LOGGER.info(USER_FOUND_BY_EMAIL + email);
//            return userDetails;
//        }
//    }

//    private void validateLoginAttempt(User user) {
//        if(user.isNotLocked()){
//            if(loginAttemptService.hasExceededMaxAttempts(user.getEmail())){
//                user.setNotLocked(false);
//            }else{
//                user.setNotLocked(true);
//            }
//        }else{
//            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
//        }
//    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }


    @Override
    public void delete(UUID id) throws UserNotFoundException {
        var countById = userRepository.countUserById(id);
        var user = userRepository.findById(id).get();
        if (countById == 0) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID + id);
        }
        user.getInterests().clear();
        user.getSkills().clear();
        userRepository.save(user);
        userRepository.deleteById(id);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

//    @Override
//    public User updateProfileImage(UUID id, MultipartFile profileImage) throws UserNotFoundException {
//        return null;
//    }

}