package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.entity.ConnectionRequestStatus;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.ConnectUserDTO;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService{

    private final ConnectionRequestRepository connectionRequestRepository;

    private final UserRepository userRepository;


    @Override
    public List<ConnectionRequestDTO> getSentConnectionRequests(UUID senderId) {
        List<ConnectionRequest> connectionRequests = connectionRequestRepository.findAllByRequesterId(senderId);
        return connectionRequests.stream()
                .map(connectionRequest -> map(connectionRequest))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConnectionRequestDTO> getReceivedConnectionRequests(UUID receiverId) {
        List<ConnectionRequest> connectionRequests = connectionRequestRepository.findAllByRequesteeId(receiverId);
        return connectionRequests.stream()
                .map(connectionRequest -> map(connectionRequest))
                .collect(Collectors.toList());
    }

    @Override
    public ConnectionRequest sendConnectionRequest(UUID senderId, UUID receiverId) throws UserNotFoundException {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);

        if (sender.isEmpty() || receiver.isEmpty()) {
            throw new UserNotFoundException("Sender or receiver not found");
        }
        else if(connectionRequestRepository.findAll().stream()
                .anyMatch(connectionRequest -> connectionRequest.getRequester().getId().equals(senderId)
                && connectionRequest.getRequestee().getId().equals(receiverId)
                &&(connectionRequest.getStatus().equals(ConnectionRequestStatus.PENDING)
                        || connectionRequest.getStatus().equals(ConnectionRequestStatus.ACCEPTED)))
        ){
            throw new UserNotFoundException("Connection exists or is still pending!");
        }
        else{
            ConnectionRequest connectionRequest = new ConnectionRequest();
            connectionRequest.setRequester(sender.get());
            connectionRequest.setRequestee(receiver.get());
            connectionRequest.setStatus(ConnectionRequestStatus.PENDING);

            return connectionRequestRepository.save(connectionRequest);
        }
    }

    @Override
    public void acceptConnectionRequest(UUID requestId, UUID receiverId) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findAllByRequesteeId(receiverId).stream()
                .filter(request -> request.getRequester().getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Connection request not found"));

        if (connectionRequest.getStatus() == ConnectionRequestStatus.PENDING) {
            connectionRequest.setStatus(ConnectionRequestStatus.ACCEPTED);
            connectionRequestRepository.save(connectionRequest);
        }
    }


    @Override
    public void rejectConnectionRequest(UUID requestId, UUID receiverId) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findAllByRequesteeId(receiverId).stream()
                .filter(request -> request.getRequester().getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Connection request not found"));
        if (connectionRequest != null && connectionRequest.getStatus() == ConnectionRequestStatus.PENDING) {
            connectionRequest.setStatus(ConnectionRequestStatus.REJECTED);
            connectionRequestRepository.save(connectionRequest);
        }
    }



    public Map<ConnectUserDTO, Integer> getProfileSuggestions(UUID userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<User> friends = getFriends(userId).stream()
                .map(friend -> userRepository.findById(friend.getId()).orElse(null))
                .toList();

        Set<User> suggestedUsers = new HashSet<>(); // To prevent duplicates

        for (User friend : friends) {
            List<User> friendFriends = getFriends(friend.getId()).stream()
                    .map(friendOfFriend -> userRepository.findById(friendOfFriend.getId()).orElse(null))
                    .toList();

            for (User friendOfFriend : friendFriends) {
                if (!friends.contains(friendOfFriend) && !user.get().equals(friendOfFriend)) {
                    List<User> commonFriends = getFriends(friendOfFriend.getId()).stream()
                            .map(friendOfFriendFriend -> userRepository.findById(friendOfFriendFriend.getId()).orElse(null))
                            .filter(commonFriend -> friends.contains(commonFriend))
                            .toList();

                    if (!commonFriends.isEmpty()) {
                        suggestedUsers.add(friendOfFriend);
                    }
                }
            }
        }

        List<ConnectUserDTO> popularAccounts = getPopularAccounts();
        UUID currentUserID = user.get().getId();

        popularAccounts = popularAccounts.stream()
                .filter(account -> !account.getId().equals(currentUserID))
                .collect(Collectors.toList());

        Map<ConnectUserDTO, Integer> suggestions = new HashMap<>();

        for (User suggestedUser : suggestedUsers) {
            List<User> suggestedUserFriends = getFriends(suggestedUser.getId()).stream()
                    .map(suggestedFriend -> userRepository.findById(suggestedFriend.getId()).orElse(null))
                    .toList();

            int commonFriendsCount = 0;

            for (User commonFriend : suggestedUserFriends) {
                if (friends.contains(commonFriend)) {
                    commonFriendsCount++;
                }
            }

            suggestions.put(mapUserToDto(suggestedUser), commonFriendsCount);
        }

        int remainingSlots = 6 - suggestions.size();
        for (int i = 0; i < popularAccounts.size(); i++) {
            ConnectUserDTO popularAccount = popularAccounts.get(i);

            boolean isFriend = friends.stream().anyMatch(friend -> friend.getId().equals(popularAccount.getId()));
            boolean isSuggested = suggestedUsers.stream().anyMatch(suggested -> suggested.getId().equals(popularAccount.getId()));

            if (!isFriend && !isSuggested && remainingSlots > 0) {
                suggestions.put(popularAccount, 0);
            }
        }
        return suggestions;
    }

    @Override
    public List<ConnectUserDTO> getFriends(UUID userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<ConnectionRequest> acceptedRequests = connectionRequestRepository.findAll().stream()
                .filter(request -> request.getStatus() == ConnectionRequestStatus.ACCEPTED)
                .filter(request -> request.getRequester().equals(user.get()) || request.getRequestee().equals(user.get()))
                .toList();

        List<ConnectUserDTO> friends = acceptedRequests.stream()
                .flatMap(request -> {
                    User requester = request.getRequester();
                    User requestee = request.getRequestee();
                    if (requester.getId().equals(userId)) {
                        return Stream.of(requestee);
                    } else {
                        return Stream.of(requester);
                    }
                })
                .distinct()
                .map(this::mapUserToDto)
                .collect(Collectors.toList());

        return friends;
    }




    public List<ConnectionRequest> getAcceptedConnectionRequests() {
        return  connectionRequestRepository.findByStatus(ConnectionRequestStatus.ACCEPTED);
    }

    public List<ConnectUserDTO> getPopularAccounts() {
        List<ConnectionRequest> acceptedRequests = getAcceptedConnectionRequests();
        Map<UUID, Integer> popularityMap = new HashMap<>();

        for (ConnectionRequest request : acceptedRequests) {
            UUID requesteeId = request.getRequestee().getId();
            UUID requesterId = request.getRequester().getId();

            popularityMap.put(requesteeId, popularityMap.getOrDefault(requesteeId, 0) + 1);
            popularityMap.put(requesterId, popularityMap.getOrDefault(requesterId, 0) + 1);
        }

        List<ConnectUserDTO> popularAccounts = popularityMap.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(20)
                .map(entry -> mapUserToDto(Objects.requireNonNull(userRepository.findById(entry.getKey()).orElse(null))))
                .collect(Collectors.toList());

        return popularAccounts;
    }


    public ConnectionRequestDTO map(ConnectionRequest connectionRequests) {
        var dto = new ConnectionRequestDTO();
        dto.setId(connectionRequests.getId());
        dto.setRequester(mapUserToDto(connectionRequests.getRequester()));
        dto.setRequestee(mapUserToDto(connectionRequests.getRequestee()));
        dto.setStatus(connectionRequests.getStatus());
        return dto;
    }

    public ConnectUserDTO mapUserToDto(User user){
        var dto = new ConnectUserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstname());
        dto.setLastName(user.getLastname());
        dto.setProfilePicture(user.getProfilePicUrl());
        return dto;
    }
}
