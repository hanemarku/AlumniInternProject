package com.example.AlumniInternProject.chat.repositories;

import com.example.AlumniInternProject.chat.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID> {
}
