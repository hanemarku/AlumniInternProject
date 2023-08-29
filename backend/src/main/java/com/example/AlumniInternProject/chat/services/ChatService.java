package com.example.AlumniInternProject.chat.services;

import com.example.AlumniInternProject.chat.models.Chat;
import com.example.AlumniInternProject.chat.models.ChatDTO;
import com.example.AlumniInternProject.exceptions.ChatNotFoundException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;


public interface ChatService{
    List<ChatDTO> getGroupChats();
    List<ChatDTO> getPrivateChats();
    List<ChatDTO> getChats();
    Chat createPrivateChat(UUID receiverId, UUID senderId) throws UserNotFoundException;
    List<ChatDTO> getPrivateChats(UUID userId) throws UserNotFoundException;
    List<ChatDTO> getGroupChats(UUID userId) throws UserNotFoundException;
    ChatDTO mapChatToChatDTO(Chat chat);
    Chat getChatById(UUID id) throws ChatNotFoundException;

}
