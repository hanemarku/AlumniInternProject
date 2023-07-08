package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.entity.User;

public class UserConverter {

    public static UserDTO convertUserEntityToDto(User userEntity) {
        UserDTO userDto = new UserDTO();

        userDto.setFirstname(userEntity.getFirstname());
        userDto.setLastname(userEntity.getLastname());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(userEntity.getRole());
        userDto.setBirthday(userEntity.getBirthday());
        userDto.setBio(userEntity.getBio());
        userDto.setCity(userEntity.getCity());
        userDto.setCountry(userEntity.getCountry());
        userDto.setEnabled(userEntity.isEnabled());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setProfilePicUrl(userEntity.getProfilePicUrl());
        userDto.setSkills(userEntity.getSkills());
        userDto.setInterests(userEntity.getInterests());

        return userDto;
    }
}
