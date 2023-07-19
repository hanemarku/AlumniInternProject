package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ConnectionRequestService {
    List<ConnectionRequest> getSentConnectionRequests(UUID senderId);
    List<ConnectionRequest> getReceivedConnectionRequests(UUID receiverId);
    ConnectionRequest sendConnectionRequest(UUID senderId, UUID receiverId) throws UserNotFoundException;
    void acceptConnectionRequest(UUID requestId, UUID receiverId);
    void rejectConnectionRequest(UUID requestId, UUID receiverId);
}
