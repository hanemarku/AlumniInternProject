import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private webSocketSubject: WebSocketSubject<any>;
  private privateMessages: Map<string, string[]> = new Map(); // Room ID to messages mapping

  constructor() {
    this.webSocketSubject = webSocket('ws://localhost:8080/ws'); 
  }

  sendMessage(message: string): void {
    this.webSocketSubject.next({ type: 'message', content: message });
  }

  receiveMessage() {
    return this.webSocketSubject.asObservable();
  }

  sendPrivateMessage(senderId: string, receiverId: string, message: string): void {
    const roomId = this.generateChatRoomId(senderId, receiverId);
    if (!this.privateMessages.has(roomId)) {
      this.privateMessages.set(roomId, []);
    }
    const roomMessages = this.privateMessages.get(roomId);
    if (roomMessages) {
      roomMessages.push(message);
      // Send the message to the corresponding WebSocket room
      // You'll need to implement this logic based on your WebSocket server's capabilities
    } else {
      console.error(`Room messages for room ID ${roomId} are undefined.`);
    }
  }


  // Method to retrieve private messages for a chat room
  getPrivateMessages(senderId: string, receiverId: string): string[] {
    const roomId = this.generateChatRoomId(senderId, receiverId);
    return this.privateMessages.get(roomId) || [];
  }

  private generateChatRoomId(user1Id: string, user2Id: string): string {
    // You can generate a unique chat room ID based on user IDs
    return `${user1Id}-${user2Id}`;
  }







//   private webSocketSubject: WebSocketSubject<any>;

//   constructor() {
//     // Initialize the WebSocket subject
//     this.webSocketSubject = webSocket('wss://your-websocket-url'); // Replace with your WebSocket URL
//   }

//   // Method to send a message via WebSocket
//   sendMessage(message: string): void {
//     this.webSocketSubject.next({ type: 'message', content: message });
//   }

//   // Method to receive messages via WebSocket
//   receiveMessage() {
//     return this.webSocketSubject.asObservable();
//   }

//   // WebSocketService

// private chatMessages: Map<string, string[]> = new Map(); // Room ID to messages mapping

// // Define a Map to store chat rooms for each user
// private userChatRooms: Map<string, string> = new Map(); // User ID to chat room ID mapping

// // Method to join a private chat room
// joinPrivateChat(user1Id: string, user2Id: string): void {
//   const roomId = this.generateChatRoomId(user1Id, user2Id);
//   this.userChatRooms.set(user1Id, roomId);
//   this.userChatRooms.set(user2Id, roomId);
//   // Initialize the chat room if it doesn't exist in chatMessages
//   if (!this.chatMessages.has(roomId)) {
//     this.chatMessages.set(roomId, []);
//   }
// }

// // Method to send a private message
// sendPrivateMessage(senderId: string, receiverId: string, message: string): void {
//   const roomId = this.userChatRooms.get(senderId);
//   if (roomId) {
//     // this.chatMessages.get(roomId).push(message);
//     // Send the message to the corresponding WebSocket room
//     // You'll need to implement this logic based on your WebSocket server's capabilities
//   }
// }

// // Method to retrieve private messages for a chat room
// getPrivateMessages(roomId: string): string[] {
//   return this.chatMessages.get(roomId) || [];
// }

// private generateChatRoomId(user1Id: string, user2Id: string): string {
//   // You can generate a unique chat room ID based on user IDs
//   return `${user1Id}-${user2Id}`;
// }

}