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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    @PostMapping("/newMessage")
    public ResponseEntity<MessageDTO> newMessage(@RequestBody MessageRequest request){
        System.out.println("message: "+request.getChatId());
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
        System.out.println("message: "+message.getMessage());
        System.out.println("id: "+id);
        System.out.println("sender id" + message.getSenderId());
        Message msg = messageService.sendMessage(message, UUID.fromString(id));
        return msg;


    }

}
