package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.admin.settings.country.CountryRepository;
import com.example.AlumniInternProject.education.EducationImpl;
import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.EducationHistory;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final EducationImpl educationService;

    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public UserGetDto save(UserDTO userDto) {
        String encodedPassword = encodePassword(userDto.getPassword());

        // Create the User entity
        var user = new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getEmail(),
                userDto.isEnabled(),
                userDto.getBirthday(),
                userDto.getProfilePicUrl(),
                userDto.getPhoneNumber(),
                userDto.getCity(),
                userDto.getCountry(),
                encodedPassword,
                userDto.getBio(),
                userDto.getSkills(),
                userDto.getInterests(),
                userDto.getRole(),
                userDto.getEmploymentHistories(),
                userDto.getEducationHistories()
        );

        //set user_id to educationHistory
        userDto.getEmploymentHistories().forEach(employmentDto -> employmentDto.setUser(user));
        userDto.getEducationHistories().forEach(educationDto -> educationDto.setUser(user));
        // Save the User entity first
        var savedUser = userRepository.save(user);

        return map(savedUser);
    }

    private EducationHistory mapEducationHistoryDtoToEntity(EducationHistory educationDto, User user) {
        // Create a new EducationHistory object and manually map the data from EducationDto
        EducationHistory educationHistory = new EducationHistory(
                educationDto.getInstitutionName(),
                educationDto.getFieldOfQualification(),
                educationDto.getFieldOfStudy(),
                educationDto.getStartDate(),
                educationDto.getEndDate(),
                educationDto.getFinalGrade(),
                educationDto.getWebsite(),
                educationDto.getCity(),
                educationDto.getCountry()
        );

        // Associate the User with the EducationHistory
        educationHistory.setUser(user);

        return educationHistory;
    }




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
        dto.setPassword(user.getPassword());
        dto.setBio(user.getBio());
        dto.setBirthday(user.getBirthday());
        dto.setSkills(user.getSkills());
        dto.setInterests(user.getInterests());
        dto.setRole(user.getRole());
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
        dto.setPassword(user.getPassword());
        dto.setBio(user.getBio());
        dto.setBirthday(user.getBirthday());
        dto.setSkills(user.getSkills());
        dto.setInterests(user.getInterests());
        dto.setRole(user.getRole());
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
    public UserGetDto update(UUID id, UserDTO dto) {
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
            user.setPassword(dto.getPassword());
            user.setBio(dto.getBio());
            user.setSkills(dto.getSkills());
            user.setInterests(dto.getInterests());
            user.setRole(dto.getRole());
            var savedUser = userRepository.save(user);
            return map(savedUser);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("User with id " + id + " does not exist");
        }
    }

    public User get(UUID id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User with id " + id + " does not exist");
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


    @Override
    public void delete(UUID id) throws UserNotFoundException {
        var countById = userRepository.countUserById(id);
        var user = userRepository.findById(id).get();
        if (countById == 0) {
            throw new UserNotFoundException("User with id " + id + " does not exist");
        }
        user.getInterests().clear();
        user.getSkills().clear();
        userRepository.save(user);
        userRepository.deleteById(id);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
