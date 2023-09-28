package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.chat.models.UserChatDTO;
import com.example.AlumniInternProject.entity.Country;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.EmailExistException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.UserDTO;
import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import com.example.AlumniInternProject.user.DTOs.UsersListingDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserGetDto save(UserDTO userDto) throws UserNotFoundException, EmailExistException;
    List<UsersListingDTO> findAll();

    UserGetDto update(UUID id, UserDTO dto) throws UserNotFoundException;
    void delete(UUID id) throws UserNotFoundException;
    List<Country> listAllCountries();

    User get(UUID id) throws UserNotFoundException;
    void updateEnabledStatus(UUID id, boolean status);
    boolean isEmailAvailable(String email);
    User findUserByEmail(String email);

    String generateRrofileImageUrl(String email);

    String fixProfileImagePath(String profileImagePath);

    UsersListingDTO findByEmail(String email) throws UserNotFoundException;
    UsersListingDTO findByID(UUID id) throws UserNotFoundException;

   boolean resetPassword(String token, String newPassword) throws UserNotFoundException;
   String encodePassword(String password);
   UsersListingDTO mapForListing(User user);

    User getUserByFirstname(String firstName);
    UserChatDTO mapUserToDto(User user);

    String getUserFullName(UUID id) throws UserNotFoundException;

    List<UserChatDTO> searchInChat(String keyword);
    List<UserChatDTO> findAllUsers();





    UserDetails loadUserByUsername(String email) throws UserNotFoundException;

    UsersListingDTO getUserById(UUID id) throws UserNotFoundException;
}
