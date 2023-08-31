package com.example.AlumniInternProject.chat.controllers;

import com.example.AlumniInternProject.chat.models.*;
import com.example.AlumniInternProject.chat.repositories.ChatRepository;
import com.example.AlumniInternProject.chat.services.ChatService;
import com.example.AlumniInternProject.chat.services.MessageService;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.exceptions.ChatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/newMessage")
    public ResponseEntity<MessageDTO> newMessage(@RequestBody MessageRequest request){
        try{
            MessageDTO messageDTO = messageService.newMessage(request.getMessage(), request.getSenderId(), request.getChatId());
            return ResponseEntity.ok(messageDTO);
        } catch (ChatNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @SuppressWarnings("deprecation")
    @MessageMapping("/{id}")
    @SendTo("/group/{id}")
    public Message greeting(Message message, @DestinationVariable("id") String id) throws Exception, ChatNotFoundException {
        Message msg = messageService.sendMessage(message, UUID.fromString(id));
        return msg;
    }

    @GetMapping("/getMessagesOfChat/{chatId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesOfChat(@PathVariable UUID chatId) throws ChatNotFoundException {
        List<ChatMessageDTO> messages = messageService.getMessagesOfChat(chatId);
        return ResponseEntity.ok(messages);
    }

}
