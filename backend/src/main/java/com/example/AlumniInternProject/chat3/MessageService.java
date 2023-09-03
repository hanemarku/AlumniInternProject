//package com.example.AlumniInternProject.chat;
//
//import com.example.AlumniInternProject.entity.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//
//public class MessageService {
//
//    private final MessageRepository messageRepository;
//
//    public Message sendMessage(UUID senderId, UUID receiverId, String content) {
//        Message message = new Message();
//        message.setSenderId(senderId);
//        message.setReceiverId(receiverId);
//        message.setContent(content);
//        message.setTimestamp(java.time.LocalDateTime.now());
//        return messageRepository.save(message);
//    }
//
//    public List<Message> getConversation(UUID user1Id, UUID user2Id) {
//        return messageRepository.findConversation(user1Id, user2Id);
//    }
//
//
//    public Optional<Message> getMessageById(UUID messageId) {
//        return messageRepository.findById(messageId);
//    }
//
//    public void deleteMessageById(UUID messageId) {
//        messageRepository.deleteById(messageId);
//    }
//
//
////    public void markMessageAsRead(UUID messageId) {
////        Optional<Message> optionalMessage = messageRepository.findById(messageId);
////        if (optionalMessage.isPresent()) {
////            Message message = optionalMessage.get();
////            message.setRead(true);
////            messageRepository.save(message);
////        }
////    }
//
//}
