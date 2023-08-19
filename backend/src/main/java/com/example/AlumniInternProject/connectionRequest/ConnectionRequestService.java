package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.ConnectUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ConnectionRequestService {
    List<ConnectionRequestDTO> getSentConnectionRequests(UUID senderId);
    List<ConnectionRequestDTO> getReceivedConnectionRequests(UUID receiverId);
    ConnectionRequest sendConnectionRequest(UUID senderId, UUID receiverId) throws UserNotFoundException;
    void acceptConnectionRequest(UUID requestId, UUID receiverId);
    void rejectConnectionRequest(UUID requestId, UUID receiverId);
    Map<ConnectUserDTO, Integer> getProfileSuggestions(UUID userId) throws UserNotFoundException;
    List<ConnectUserDTO> getFriends(UUID userId) throws UserNotFoundException;
}
