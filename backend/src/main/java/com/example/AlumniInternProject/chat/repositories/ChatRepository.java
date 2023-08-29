package com.example.AlumniInternProject.chat.repositories;

import com.example.AlumniInternProject.chat.models.Chat;
import com.example.AlumniInternProject.chat.models.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, UUID> {
    List<Chat> findAllByType(ChatType type);
    List<Chat> findAll();
    List<Chat> findAllByUsersId(UUID userId);
    Chat findChatById(UUID id);
}
