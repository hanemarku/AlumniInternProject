package com.example.AlumniInternProject.chat.services;

import com.example.AlumniInternProject.chat.models.*;
import com.example.AlumniInternProject.chat.repositories.ChatRepository;
import com.example.AlumniInternProject.exceptions.ChatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    @Override
    public MessageDTO newMessage(String message, UUID senderId, UUID chatId) throws ChatNotFoundException {
        Chat chat = chatService.getChatById(chatId);
        Date time = new Date();
        Message msg = new Message();
        msg.setMessage(message);
        msg.setTime(time);
        msg.setChat(chat);
        msg.setSenderId(senderId);
        chat.getMessages().add(msg);
        chatRepository.save(chat);
        return mapMessageToMessageDTO(msg);
    }

    public Message sendMessage(Message message, UUID chatId) throws ChatNotFoundException {
        Chat chat = chatService.getChatById(chatId);
        message.setChat(chat);
        chat.getMessages().add(message);
        chatRepository.save(chat);
        message.setTime(new Date());
        message.setSenderId(message.getSenderId());
        return message;
    }

    @Override
    public List<ChatMessageDTO> getMessagesOfChat(UUID chatId) throws ChatNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) {
            throw new ChatNotFoundException();
        } else {
            List<ChatMessageDTO> messageDTOs = chat.getMessages().stream()
                    .map(this:: mapToMessageMessageChatDTO)
                    .sorted(Comparator.comparing(ChatMessageDTO::getTime)) // Sort by message timestamp
                    .collect(Collectors.toList());
            return messageDTOs;
        }
    }



    public MessageDTO mapMessageToMessageDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setTime(message.getTime());
        messageDTO.setChat(chatService.mapChatToChatDTO(message.getChat()));
        messageDTO.setTimejs(message.getTime().getHours()+":"+message.getTime().getMinutes());
        return messageDTO;
    }

    public ChatMessageDTO mapToMessageMessageChatDTO(Message message){
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSenderId(message.getSenderId());
        chatMessageDTO.setMessage(message.getMessage());
        chatMessageDTO.setTime(message.getTime());
        return chatMessageDTO;
    }
}
