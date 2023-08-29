package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.Verfication.VerificationTokenRepository;
import com.example.AlumniInternProject.Verfication.VerificationTokenService;
import com.example.AlumniInternProject.admin.settings.country.CountryRepository;
import com.example.AlumniInternProject.chat.models.UserChatDTO;
import com.example.AlumniInternProject.entity.*;
import com.example.AlumniInternProject.enumerations.Role;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.*;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
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
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final LoginAttemptService loginAttemptService;
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

        String randomCode = RandomString.make(64);

        var user = new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getEmail(),
                false,
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
                userDto.getAuthorities(),
                userDto.getVerificationCode()
//                true,
//                true
        );

        userDto.setAuthorities(authorities);
        user.setAuthorities(authorities);
        userDto.getEmploymentHistories().forEach(employmentDto -> employmentDto.setUser(user));
        userDto.getEducationHistories().forEach(educationDto -> educationDto.setUser(user));
        userDto.setVerificationCode(randomCode);

        var savedUser = userRepository.save(user);
        LOGGER.info("New user was created with password: " + user.getPassword());
//        emailService.sendNewPasswordEmail(userDto.getFirstname(), userDto.getEmail(), password);
//        emailService.sendNewPasswordEmail(userDto.getFirstname(), password, userDto.getEmail());

        try{
            String siteURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            System.out.println(siteURL);
            String token = UUID.randomUUID().toString();
            verificationTokenService.save(savedUser , token, VerificationType.EMAIL_VERIFICATION);
            emailService.sendVerificationEmail(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map(savedUser);
    }

    @Override
    public boolean resetPassword(String token, String newPassword) throws UserNotFoundException {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if (verificationToken != null) {
            UUID userId = verificationToken.getUser().getId();
            User user = findById(userId);
            if (!user.isEnabled() || verificationToken.getExpirationDate().before(new Timestamp(System.currentTimeMillis()))) {
                return false;
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            verificationTokenService.removeTokenByUserAndType(user.getId(), VerificationType.PASSWORD_RESET);
            return true;
        }

        return false;
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
    public String generateRrofileImageUrl(String email) {
        String[] emailParts = email.split("@");
        if (emailParts.length == 2) {
            email = emailParts[0];
        }
        email = email.replace(".", "_");
        return email;
    }

    @Override
    public String fixProfileImagePath(String profileImagePath) {
        String forwardSlashesPath = profileImagePath.replace("\\", "/");
        int startIndex = forwardSlashesPath.indexOf("user-photos");
        if (startIndex == -1) {
            return forwardSlashesPath;
        }
        String relativePath = forwardSlashesPath.substring(startIndex);
        return relativePath;
    }

    @Override
    public User getUserByFirstname(String firstName) {
        return userRepository.getUserByFirstname(firstName);
    }


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
//        User user = userRepository.findUserByEmail(email);
//        if(user == null) {
//            LOGGER.error("User not found by email: " + email);
//            throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email);
//        }else {
//            userRepository.save(user);
//            ALumniUserDetails userDetails = new ALumniUserDetails(user);
//            LOGGER.info("Returning found user by email: " + email);
//            return userDetails;
//        }
//    }

        @Override
        public UserDetails loadUserByUsername(String email) throws  UserNotFoundException {
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                LOGGER.error("User not found in the database" + email);
                throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + email);
            }else{
//                validateLoginAttempt(user);
                userRepository.save(user);
                ALumniUserDetails userDetails = new ALumniUserDetails(user);
                LOGGER.info(USER_FOUND_BY_EMAIL + email);
                return userDetails;
            }
        }





    @Transactional
    @Override
    public List<UsersListingDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> mapForListing(user))
                .collect(Collectors.toList());
    }


    @Override
    public UsersListingDTO findByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
//                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        return mapForListing(user);
    }

    @Override
    public UsersListingDTO findByID(UUID id) throws UserNotFoundException {
        User user =  userRepository.findUserById(id);
        return mapForListing(user);
    }


    public User findById(UUID id) throws UserNotFoundException {
        User user = userRepository.findUserById(id);
        if(user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID + id);
        }else {
            return user;
        }
    }


    @Override
    public UsersListingDTO mapForListing(User user) {
        var dto = new UsersListingDTO();
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
        List<EducationHistoryDTO> educationHistories = user.getEducationHistories().stream()
                .map(e -> new EducationHistoryDTO(e.getInstitutionName(), e.getFieldOfQualification(), e.getFieldOfStudy(), e.getStartDate(), e.getEndDate(), e.getFinalGrade(), e.getWebsite(), e.getCity(), e.getCountry()))
                .collect(Collectors.toList());
        dto.setEducationHistories(educationHistories);


        List<EmploymentHistoryDTO> employmentHistories = user.getEmploymentHistories().stream()
                .map(e -> new EmploymentHistoryDTO(e.getMainActivities(), e.getOccupationPosition(), e.getCompanyName(), e.getDepartment(), e.isOngoing(), e.getFromDate(), e.getToDate(), e.getCity(), e.getCountry(), e.getSkills()))
                .collect(Collectors.toList());
        dto.setEmploymentHistories(employmentHistories);
        return dto;
    }


    private UserGetDto map(User user) {
        String profileImageUrl = "";

        if(user.getProfilePicUrl() == null) {
            profileImageUrl = getTemporaryProfileImageUrl(user.getFirstname());
        }else {
            profileImageUrl = generateRrofileImageUrl(user.getEmail());
        }
        var dto = new UserGetDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setBirthday(user.getBirthday());
        dto.setProfilePicUrl(profileImageUrl);
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
        dto.setVerificationCode(user.getVerificationCode());

        return dto;
    }





    public List<Country> listAllCountries() {
        return (List<Country>) countryRepository.findAll();
    }

//    @Override
//    public User findUserById(UUID id) {
//        return userRepository.findUserById(id);\
//    }

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

    private void validateLoginAttempt(User user) {
        if(user.isEnabled()){
            if(loginAttemptService.hasExceededMaxAttempts(user.getEmail())){
                user.setEnabled(false);
            }else{
                user.setEnabled(true);
            }
        }else{
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }

    @Override
    public UserChatDTO mapUserToDto(User user){
        var dto = new UserChatDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setProfilePicUrl(user.getProfilePicUrl());
        return dto;
    }

    @Override
    public String getUserFullName(UUID id) throws UserNotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID + id);
        }else{
            return user.getFirstname() + " " + user.getLastname();
        }
    }

    @Override
    public List<UserChatDTO> searchInChat(String keyword) {
       List<UserChatDTO> users = userRepository.findByKeyword(keyword).stream()
               .map(this::mapUserToDto)
               .toList();
       return users;
    }

    public List<UserChatDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

//    @Override
//    public User updateProfileImage(UUID id, MultipartFile profileImage) throws UserNotFoundException {
//        return null;
//    }

}