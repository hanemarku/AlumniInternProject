package com.example.AlumniInternProject.chat.services;

import com.example.AlumniInternProject.chat.models.*;
import com.example.AlumniInternProject.chat.repositories.ChatRepository;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.ChatNotFoundException;
import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.UserService;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public List<ChatDTO> getGroupChats() {
        List<Chat> groupChats = chatRepository.findAllByType(ChatType.GROUP);
        return groupChats.stream()
                .map(this::mapChatToChatDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> getPrivateChats(UUID userId) throws UserNotFoundException {
        User user = userService.get(userId);

        if (user == null) {
            return Collections.emptyList();
        }

        List<Chat> userChats = chatRepository.findAllByUsersId(userId);

        List<ChatDTO> privateChats = userChats.stream()
                .filter(chat -> chat.getType() == ChatType.PRIVATE)
                .map(chat -> {
                    ChatDTO chatDTO = mapChatToChatDTO(chat);
                    chatDTO.getUsers().removeIf(u -> u.getId().equals(userId));
                    return chatDTO;
                })
                .collect(Collectors.toList());

        return privateChats;
    }

    @Override
    public List<ChatDTO> getGroupChats(UUID userId) throws UserNotFoundException {
        User user = userService.get(userId);

        if (user == null) {
            return Collections.emptyList();
        }
        List<Chat> userChats = chatRepository.findAllByUsersId(userId);

        List<ChatDTO> groupChats = userChats.stream()
                .filter(chat -> chat.getType() == ChatType.GROUP && chat.getUsers().contains(user))
                .map(this::mapChatToChatDTO)
                .collect(Collectors.toList());
        return groupChats;
    }


    @Override
    public List<ChatDTO> getPrivateChats() {
        List<Chat> privateChats = chatRepository.findAllByType(ChatType.PRIVATE);
        return privateChats.stream()
                .map(this::mapChatToChatDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> getChats() {
        List<Chat> chats = chatRepository.findAll();
        return chats.stream()
                .map(this::mapChatToChatDTO)
                .collect(Collectors.toList());
    }

    public Set<UserChatDTO> mapUsersToUserDTOs(Set<User> users) {
        return users.stream()
                .map(userService::mapUserToDto) // Use userService here
                .collect(Collectors.toSet());
    }

    @Override
    public Chat getChatById(UUID id) throws ChatNotFoundException {
        Chat chat = chatRepository.findById(id).get();
        if (chat == null) {
            throw new ChatNotFoundException();
        }else{
            return chat;
        }
    }


    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public Chat createPrivateChat(UUID receiverId, UUID senderId) throws UserNotFoundException {
        User sender = userService.get(senderId);
        User receiver = userService.get(receiverId);

            if (sender != null && receiver != null) {
                Chat chat = new Chat();
                chat.setAdmin(sender.getId());
                chat.setType(ChatType.PRIVATE);
                chat.getUsers().add(sender);
                chat.getUsers().add(receiver);
                sender.getChats().add(chat);
                receiver.getChats().add(chat);
                chatRepository.save(chat);
                return chat;
            }else {
                throw new UserNotFoundException("User not found");
            }
    }

    @Override
    public Chat createGroupChat(String name, UUID groupAdmin) throws UserNotFoundException {
        User admin = userService.get(groupAdmin);
        if (admin != null) {
            Chat chat = new Chat();
            chat.setAdmin(admin.getId());
            chat.setType(ChatType.GROUP);
            chat.setName(name);
            chat.getUsers().add(admin);
            admin.getChats().add(chat);
            chatRepository.save(chat);
            return chat;
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void addUsersToChat(UUID chatId, Set<UUID> userIds) throws ChatNotFoundException, UserNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) {
            throw new ChatNotFoundException();
        }else {
            for (UUID userId : userIds) {
                User user = userService.get(userId);
                if (user != null) {
                    chat.getUsers().add(user);
                    user.getChats().add(chat);
                }else {
                    throw new UserNotFoundException("User not found");
                }
            }
            chatRepository.save(chat);
        }
    }

    @Override
    public void addUserToChat(UUID chatId, UUID userId) throws ChatNotFoundException, UserNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) {
            throw new ChatNotFoundException();
        }else {
            User user = userService.get(userId);
            if (user != null) {
                chat.getUsers().add(user);
                user.getChats().add(chat);
            }else {
                throw new UserNotFoundException("User not found");
            }
            chatRepository.save(chat);
        }
    }

    @Override
    public ChatDTO mapChatToChatDTO(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setId(chat.getId());
        chatDTO.setAdmin(chat.getAdmin());
        chatDTO.setType(chat.getType());
        chatDTO.setName(chat.getName());
        chatDTO.setUsers(mapUsersToUserDTOs(chat.getUsers()));
        return chatDTO;
    }
}

