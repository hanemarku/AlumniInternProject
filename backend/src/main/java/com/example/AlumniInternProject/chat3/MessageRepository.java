//package com.example.AlumniInternProject.chat;
//
//import com.example.AlumniInternProject.entity.Message;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface MessageRepository extends JpaRepository<Message, UUID> {
//    @Query("SELECT m FROM Message m WHERE (m.senderId = :user1Id AND m.receiverId = :user2Id) OR (m.senderId = :user2Id AND m.receiverId = :user1Id) ORDER BY m.timestamp")
//    List<Message> findConversation(UUID user1Id, UUID user2Id);
//
//    //find message by
//}
