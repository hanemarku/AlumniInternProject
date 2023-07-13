package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/connectionRequest")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;

    @GetMapping("/{user_id}/myConnectionRequestReceived")
    public List<ConnectionRequest> getMyConnectionRequestReceived(@PathVariable UUID user_id) {
        return connectionRequestService.getReceivedConnectionRequests(user_id);
    }

    @GetMapping("/{user_id}/myConnectionRequestSent")
    public List<ConnectionRequest> getMyConnectionRequestSent(@PathVariable UUID user_id) {
        return connectionRequestService.getSentConnectionRequests(user_id);
    }

    @PostMapping("/{sender_id}/sendConnectionRequest/{receiver_id}")
    public ConnectionRequest sendConnectionRequest(@PathVariable UUID sender_id, @PathVariable UUID receiver_id) throws UserNotFoundException {
        return connectionRequestService.sendConnectionRequest(sender_id, receiver_id);
    }

    @PutMapping("/{request_id}/acceptConnectionRequest/{receiver_id}")
    public void acceptConnectionRequest(@PathVariable UUID request_id, @PathVariable UUID receiver_id) {
        connectionRequestService.acceptConnectionRequest(request_id, receiver_id);
    }

    @PutMapping("/{request_id}/rejectConnectionRequest/{receiver_id}")
    public void rejectConnectionRequest(@PathVariable UUID request_id, @PathVariable UUID receiver_id) {
        connectionRequestService.rejectConnectionRequest(request_id, receiver_id);
    }
}
