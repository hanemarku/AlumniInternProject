package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, UUID> {
    List<ConnectionRequest> findAllByRequesterId(UUID requesterId);
    List<ConnectionRequest> findAllByRequesteeId(UUID receiverId);
    Optional<ConnectionRequest> findByRequesterIdAndRequesteeId(UUID senderId, UUID receiverId);
    Optional<ConnectionRequest> findByIdAndRequesterId(UUID id, UUID senderId);
    Optional<ConnectionRequest> findByIdAndRequesteeId(UUID id, UUID receiverId);
    ConnectionRequest save(ConnectionRequest connectionRequest);
}
