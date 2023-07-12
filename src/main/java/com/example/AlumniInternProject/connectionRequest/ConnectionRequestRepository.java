package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, UUID> {
    List<ConnectionRequest> findAllByReceiverId(UUID receiverId);
    List<ConnectionRequest> findAllBySenderId(UUID senderId);
    Optional<ConnectionRequest> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
    Optional<ConnectionRequest> findByIdAndSenderId(UUID id, UUID senderId);
    Optional<ConnectionRequest> findByIdAndReceiverId(UUID id, UUID receiverId);
    ConnectionRequest save(ConnectionRequest connectionRequest);
}
