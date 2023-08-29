package com.example.AlumniInternProject.chat.services;

import com.example.AlumniInternProject.chat.models.ChatDTO;
import com.example.AlumniInternProject.chat.models.Message;
import com.example.AlumniInternProject.chat.models.MessageDTO;
import com.example.AlumniInternProject.exceptions.ChatNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface MessageService {
    MessageDTO newMessage(String message, UUID senderId, UUID chatId) throws ChatNotFoundException;
    Message sendMessage(Message message, UUID chatId) throws ChatNotFoundException;
}
