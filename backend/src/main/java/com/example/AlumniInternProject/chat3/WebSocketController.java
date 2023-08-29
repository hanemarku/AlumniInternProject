//package com.example.AlumniInternProject.chat;
//
//import com.example.AlumniInternProject.entity.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//
//public class WebSocketController {
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//
//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message sendMessage(@Payload Message message) {
//        return message;
//    }
//
//
//    @MessageMapping("/private-message")
//    public Message receivePrivateMessage(@Payload Message message) {
//        String receiverChannel = "/user/" + message.getReceiverId() + "/private";
//        simpMessagingTemplate.convertAndSend(receiverChannel, message);
//        return message;
//    }
//
//
////    private final MessageService messageService;
////
////    @MessageMapping("/chat/send")
////    @SendTo("/topic/public")
////    public Message sendMessage(Message message) {
////        message = messageService.sendMessage(message.getSenderId(), message.getReceiverId(), message.getContent());
////        return message;
////    }
//
//}
