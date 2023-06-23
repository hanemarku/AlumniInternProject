package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.User;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public UserGetDto save(UserDTO userDto) {
        var user = new User(
            userDto.getFirstname(),
            userDto.getLastname(),
            userDto.getEmail(),
            userDto.isEnabled(),
            userDto.getBirthday(),
            userDto.getProfilePicUrl(),
            userDto.getPhoneNumber(),
            userDto.getCity(),
            userDto.getPassword(),
            userDto.getBio(),
            userDto.getSkills(),
            userDto.getInterests(),
            userDto.getRole()
        );
        var saved = userRepository.save(user);
        return map(saved);
//        var savedUser = userRepository.save(user);
//        var userGetDto = modelMapper.map(savedUser, UserGetDto.class);
//        return userGetDto;
    }

    @Override
    public List<UserGetDto> findAll() {
//
//            List<User> users = userRepository.findAll();
//            return users.stream()
//                    .map(user -> modelMapper.map(user, UserGetDto.class))
//                    .collect(Collectors.toList());


//        return (List<UserDTO>) userRepository
//                .findAll();
//                .findAll(Sort.by()
//                        .ascending());

        return userRepository.findAll()
                .stream()
                .map(user -> map(user))
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
        dto.setPassword(user.getPassword());
        dto.setBio(user.getBio());
        dto.setBirthday(user.getBirthday());
        dto.setSkills(user.getSkills());
        dto.setInterests(user.getInterests());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public User findById(UUID id) {
        return null;
    }

    @Override
    public User update(UUID id, User dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
