package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.entity.ConnectionRequestStatus;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService{

    private final ConnectionRequestRepository connectionRequestRepository;

    private final UserRepository userRepository;


    @Override
    public List<ConnectionRequest> getSentConnectionRequests(UUID senderId) {
        return connectionRequestRepository.findAllByRequesterId(senderId);
    }

    @Override
    public List<ConnectionRequest> getReceivedConnectionRequests(UUID receiverId) {
        return connectionRequestRepository.findAllByRequesteeId(receiverId);
    }

    @Override
    public ConnectionRequest sendConnectionRequest(UUID senderId, UUID receiverId) throws UserNotFoundException {
        User sender = userRepository.findById(senderId).get();
        User receiver = userRepository.findById(receiverId).get();
        if (sender == null || receiver == null) {
            throw new UserNotFoundException("Sender or receiver not found");
        }else{
            ConnectionRequest connectionRequest = new ConnectionRequest(sender, receiver);
            connectionRequest.setRequester(sender);
            connectionRequest.setRequestee(receiver);
            connectionRequest.setStatus(ConnectionRequestStatus.PENDING);

            return connectionRequestRepository.save(connectionRequest);
        }
    }

    @Override
    public void acceptConnectionRequest(UUID requestId, UUID receiverId) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findAllByRequesterId(receiverId).stream()
                .filter(request -> request.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Connection request not found"));
        if (connectionRequest != null && connectionRequest.getStatus() == ConnectionRequestStatus.PENDING) {
            connectionRequest.setStatus(ConnectionRequestStatus.ACCEPTED);
            connectionRequestRepository.save(connectionRequest);
        }
    }

    @Override
    public void rejectConnectionRequest(UUID requestId, UUID receiverId) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findAllByRequesterId(receiverId).stream()
                .filter(request -> request.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Connection request not found"));
        if (connectionRequest != null && connectionRequest.getStatus() == ConnectionRequestStatus.PENDING) {
            connectionRequest.setStatus(ConnectionRequestStatus.REJECTED);
            connectionRequestRepository.save(connectionRequest);
        }
    }
}
