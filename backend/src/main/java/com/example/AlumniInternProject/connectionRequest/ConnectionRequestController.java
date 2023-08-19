package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.DTOs.ConnectUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/connectionRequest")
@CrossOrigin(origins = "http://localhost:4200")

public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;

    @GetMapping("/{user_id}/myConnectionRequestReceived")
    public ResponseEntity<Map<String,List<ConnectionRequestDTO>>> getMyConnectionRequestReceived(@PathVariable UUID user_id) {
        List<ConnectionRequestDTO> connectionRequests = connectionRequestService.getReceivedConnectionRequests(user_id);
        Map<String, List<ConnectionRequestDTO>> response = new HashMap<>();
        response.put("connectionsReceived", connectionRequests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}/myConnectionRequestSent")
    public ResponseEntity<Map<String,List<ConnectionRequestDTO>>> getMyConnectionRequestSent(@PathVariable UUID user_id) {
        List<ConnectionRequestDTO> connectionRequests = connectionRequestService.getSentConnectionRequests(user_id);
        Map<String, List<ConnectionRequestDTO>> response = new HashMap<>();
        response.put("connectionsSent", connectionRequests);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{sender_id}/sendConnectionRequest/{receiver_id}")
    public ResponseEntity<Map<String, String>> sendConnectionRequest(@PathVariable UUID sender_id, @PathVariable UUID receiver_id) {
        Map<String, String> response = new HashMap<>();

        try{
            ConnectionRequest connectionRequest = connectionRequestService.sendConnectionRequest(sender_id, receiver_id);
//            response.put("message", "Connection request sent!");
            response.put("status", connectionRequest.getStatus().toString());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{request_id}/acceptConnectionRequest/{receiver_id}")
    public void acceptConnectionRequest(@PathVariable UUID request_id, @PathVariable UUID receiver_id) {
        connectionRequestService.acceptConnectionRequest(request_id, receiver_id);
    }

    @PutMapping("/{request_id}/rejectConnectionRequest/{receiver_id}")
    public void rejectConnectionRequest(@PathVariable UUID request_id, @PathVariable UUID receiver_id) {
        connectionRequestService.rejectConnectionRequest(request_id, receiver_id);
    }

    @GetMapping("/suggestedAccounts/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getProfileSuggestions(@PathVariable UUID userId) throws UserNotFoundException {
        List<Map<String, Object>> response = new ArrayList<>();
        Map<ConnectUserDTO, Integer> suggestions = connectionRequestService.getProfileSuggestions(userId);

        for (Map.Entry<ConnectUserDTO, Integer> entry : suggestions.entrySet()) {
            Map<String, Object> suggestionMap = new HashMap<>();
            suggestionMap.put("user", entry.getKey());
            suggestionMap.put("count", entry.getValue());
            response.add(suggestionMap);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<ConnectUserDTO>> getUserFriends(@PathVariable UUID userId) throws UserNotFoundException {
        List<ConnectUserDTO> friends = connectionRequestService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }


//    @GetMapping("/suggestedAccounts/{userId}")
//    public ResponseEntity<Map<String, Map<ConnectUserDTO, Integer>>> getProfileSuggestions(@PathVariable UUID userId) throws UserNotFoundException {
//        Map<String, Map<ConnectUserDTO, Integer>> response = new HashMap<>();
//        Map<ConnectUserDTO, Integer> suggestions = connectionRequestService.getProfileSuggestions(userId);
//        response.put("suggestions", suggestions);
//        return ResponseEntity.ok(response);
//    }
}
