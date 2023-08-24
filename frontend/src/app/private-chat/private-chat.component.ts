import { Component, OnInit } from '@angular/core';
import { UserDataService } from '../services/user-service/user-data.service';
import { WebSocketService } from '../services/web-socket.service';
import { ConnectionRequestService } from '../services/connection-request/connection-request.service';
import { forkJoin } from 'rxjs';
import { SafeUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';

export interface User {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  profilePicUrl: string | SafeUrl;
}

@Component({
  selector: 'app-private-chat',
  templateUrl: './private-chat.component.html',
  styleUrls: ['./private-chat.component.sass']
})
export class PrivateChatComponent implements OnInit {
  connectedUsers: User[] = []; 
  userId: string = JSON.parse(localStorage.getItem('user') || '{}').id;
  privateChatMessages: string[] = [];


  constructor(
    private webSocketService: WebSocketService,
    private userService: UserDataService,
    private connectionService: ConnectionRequestService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchConnectedUsers(this.userId);
      //  // Subscribe to incoming chat messages from WebSocket
      this.webSocketService.receiveMessage().subscribe((message: string) => {
        // Add the received message to the chat messages array
        this.privateChatMessages.push(message);
      });
  
      this.route.params.subscribe((params) => {
        this.userId = params['userId'];
        this.startPrivateChat(this.userId);
      });
  }

  startPrivateChat(receiverId: string): void {
    const messageContent = "Hello, let's chat privately!";
    const senderId = this.userId;
    this.webSocketService.sendPrivateMessage(senderId, receiverId, messageContent);
  }


  fetchConnectedUsers(userId: string): void {
    this.connectionService.getUserFriends(userId).subscribe(
      (friends: User[]) => {
        this.connectedUsers = friends;
  
        const profilePicRequests = this.connectedUsers.map(user =>
          this.userService.getUserProfilePic(user.email)
        );
  
        forkJoin(profilePicRequests).subscribe(
          (profilePicUrls: SafeUrl[]) => {
            this.connectedUsers.forEach((friend, index) => {
              friend.profilePicUrl = profilePicUrls[index];
            });
          },
          (error) => {
            console.error('Error fetching friends:', error);
          }
        );
      },
      (error) => {
        console.error('Error fetching friends:', error);
      }
    );
  }
}

