package com.example.AlumniInternProject.chat.controllers;

import com.example.AlumniInternProject.chat.models.*;
import com.example.AlumniInternProject.chat.repositories.ChatRepository;
import com.example.AlumniInternProject.chat.services.ChatService;
import com.example.AlumniInternProject.connectionRequest.ConnectionRequestDTO;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.UserRepository;
import com.example.AlumniInternProject.user.UserService;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final UserService userService;
    private final ChatService chatService;
    private final ChatRepository chatRepository;


    @GetMapping("")
    public ResponseEntity<ChatResponse> getChatInfo() throws UserNotFoundException {
        List<ChatDTO> groupChats = chatService.getGroupChats();
        List<ChatDTO> privateChats = chatService.getPrivateChats();
        Map<UUID, String> privateChatsFullnames = new HashMap<>();
        List<ChatDTO> chats = chatService.getChats();

        for (ChatDTO chat : chats) {
            if (chat.getType() == ChatType.PRIVATE) {
                for (UserChatDTO user : chat.getUsers()) {
                    String fullname = userService.getUserFullName(user.getId());
                    privateChatsFullnames.put(user.getId(), fullname);
                }
            }
        }
        ChatResponse response = new ChatResponse();
        response.setGroupChats(groupChats);
        response.setPrivateChats(privateChats);
        response.setPrivateChatNames(privateChatsFullnames);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user_chats")
    public ResponseEntity<ChatResponse> getUserChatInfo(@RequestParam UUID id) throws UserNotFoundException {
        List<ChatDTO> groupChats = chatService.getGroupChats(id);
        List<ChatDTO> privateChats = chatService.getPrivateChats(id);
        Map<UUID, String> privateChatsFullnames = new HashMap<>();
        Map<UUID, UserChatDTO> usersChatDto = new HashMap<>();
        Map<UUID, UUID> chatUserIdMap = new HashMap<>();

        for (ChatDTO chat : privateChats) {
            for (UserChatDTO user : chat.getUsers()) {
                String fullname = userService.getUserFullName(user.getId());
                UserChatDTO userChatDTO = userService.mapUserToDto(userService.get(user.getId()));
                privateChatsFullnames.put(user.getId(), fullname);
                usersChatDto.put(user.getId(), userChatDTO);
                chatUserIdMap.put(chat.getId(), user.getId());
            }
        }
        ChatResponse response = new ChatResponse();
        response.setGroupChats(groupChats);
        response.setPrivateChats(privateChats);
        response.setPrivateChatNames(privateChatsFullnames);
        response.setUsersChatDto(usersChatDto);
        response.setChatIdUserId(chatUserIdMap);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/newchat")
    public ResponseEntity<String> newPrivateChat(@ModelAttribute ChatRequest chatRequest) throws UserNotFoundException {
        UUID receiverId = chatRequest.getId();
        UUID senderId = chatRequest.getParam1();
        try {
            chatService.createPrivateChat(receiverId, senderId);
            return ResponseEntity.ok("Chat created with ID: ");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/loggedInUser")
    public ResponseEntity<ALumniUserDetails> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            ALumniUserDetails alumniUserDetails = (ALumniUserDetails) userDetails;
            return ResponseEntity.ok(alumniUserDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
