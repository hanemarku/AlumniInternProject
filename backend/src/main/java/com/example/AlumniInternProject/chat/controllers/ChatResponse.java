package com.example.AlumniInternProject.chat.controllers;

import com.example.AlumniInternProject.chat.models.Chat;
import com.example.AlumniInternProject.chat.models.ChatDTO;
import com.example.AlumniInternProject.chat.models.UserChatDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ChatResponse {
    Map<UUID, String> privateChatNames;
    List<ChatDTO> groupChats;
    List<ChatDTO> privateChats;
    Map<UUID, UserChatDTO> usersChatDto;
    Map<UUID, UUID> chatIdUserId;
}
