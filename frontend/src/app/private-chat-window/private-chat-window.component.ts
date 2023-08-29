
import { Component, NgZone, OnInit } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { UserDataService } from '../services/user-service/user-data.service';
import { ConnectionRequestService } from '../services/connection-request/connection-request.service';
import { forkJoin } from 'rxjs';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { format } from 'date-fns';
import { ChatDTO, ChatResponse, ChatServiceService } from '../services/chat-service.service';
import { MessageService } from '../services/message.service';
// import { DatePipe } from '@angular/common';



export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePicUrl: string | SafeUrl;
}

export interface UserChatDTO {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  profilePicUrl: string | SafeUrl;
}


interface Message {
  senderId: string;
  message: string;
  time: string;
}


@Component({
  selector: 'app-private-chat-window',
  templateUrl: './private-chat-window.component.html',
  styleUrls: ['./private-chat-window.component.sass']
})

export class PrivateChatWindowComponent implements OnInit{

  loggedInUser: any = JSON.parse(localStorage.getItem('user') || '{}');
  connectedUsers: User[] = [];
  showAllUsers = false;
  showAllGroups = false;
  searchResults: UserChatDTO[] = []; 
  searchUser: string = ''; 
  filteredUsers: any[] = []; 
  privateChatUsers: UserChatDTO[] = [];
  selectedUser: UserChatDTO | null = null; 
  selectedChatId: string = '';
  public message = '';
  privateChats: ChatDTO[] = [];
  chatUserIdMap: { [key: string]: string } = {};
  stompClient: Stomp.Client | null = null;
  messages: Message[] = [];
  messageInput: string = '';
  chatMessages: { [key: string]: Message[] } = {};
  privateChatNames: { [key: string]: string } = {};
  privateChatName: string = '';



  toggleUserList() {
    this.showAllUsers = !this.showAllUsers;
  }

  toggleGroupList() {
    this.showAllGroups = !this.showAllGroups;
  }


  constructor(
    private userService: UserDataService,
    private connectionService: ConnectionRequestService,
    private chatService: ChatServiceService,
    private zone: NgZone,
    private messageService: MessageService,

  ){}

  ngOnInit(): void {
    this.fetchConnectedUsers(this.loggedInUser.id);
    console.log(this.connectedUsers)
    this.fetchUserImage(this.loggedInUser.email);
    this.fetchPrivateChats(this.loggedInUser.id);
    this.chatMessages = {};
  }


  disconnect(): void {
    if (this.stompClient !== null) {
      this.stompClient.deactivate(); 
    }
    console.log('Disconnected');
  }

  connect(userId: string, chatId: string): void {
    console.log(userId + ' ' + chatId);
    const socket = new SockJS('http://localhost:8080/ChatApp-websocket');
    this.stompClient = new Stomp.Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, 
    });
    this.stompClient.onConnect = (frame) => {
      console.log('Connected: ' + frame);
      this.stompClient?.subscribe(`/group/${chatId}`, (message) => {
        this.showMessage(userId, JSON.parse(message.body));
        console.log('Subscribed to /group/' + chatId);
      });
    };
    this.stompClient.activate();
  }

  sendMessage(): void {
    const time = new Date();
    const chatId = this.selectedChatId;
    this.stompClient?.publish({ destination: `/app/${chatId}`, body: JSON.stringify({
      'senderId': this.loggedInUser.id,
      'message': this.message,
      'time': time,
    }) });
    this.message = '';
  }

  showMessage(userId: string, message: Message): void {
    const chatId = this.selectedChatId;
    if (!this.chatMessages[chatId]) {
      this.chatMessages[chatId] = [];
    }
    this.chatMessages[chatId].push(message);
    this.privateChatName = `${this.selectedUser?.firstname} ${this.selectedUser?.lastname}`;
  }
  
  searchUsers(keyword: string): void {
    if (!keyword) {
      this.searchResults = [];
      return;
    }
    keyword = keyword.toLowerCase();
    this.userService.searchInChat(keyword).subscribe(
      (results: UserChatDTO[]) => { 
        this.searchResults = results;

        this.userService.fetchProfilePictures(this.searchResults);
          this.searchResults = results.filter((resultUser) =>
          this.connectedUsers.some(
            (connectedUser) => connectedUser.id === resultUser.id
            )
          );
      },
      (error) => {
        console.error('Error searching users:', error);
      }
    );
  }



  selectUser(user: UserChatDTO): void {;
    this.selectedUser = user;
    this.selectedChatId = this.getKeyByValue(this.chatUserIdMap, user.id) || '';
    this.privateChatName = this.selectedUser.firstname + ' ' + this.selectedUser.lastname;
    console.log('Selected user fullname:', this.privateChatName);
    this.disconnect();
    this.connect(this.loggedInUser.id, this.selectedChatId);
  }

  addToPrivateChat(user: UserChatDTO): void {
    if (!this.privateChatUsers.some(u => u.id === user.id)) {
      this.privateChatUsers.push(user);
      this.selectUser(user);
      this.selectedChatId = this.getKeyByValue(this.chatUserIdMap, user.id) || '';
      this.newChat(user.id);
    }
  }

  newChat(id: string): void {
    const param1Data = { param1: this.loggedInUser.id }; 
    this.chatService.createPrivateChat(id, param1Data).subscribe(
      (response: string) => {
        this.selectedChatId = response;
        console.log('Chat created:', response);
      },
      (error) => {
        console.error('Error creating chat:', error);
      }
    );
  }
  
  fetchPrivateChats(userId: string): void {
    this.chatService.getUserChatInfo(userId).subscribe(
      (chatResponse: ChatResponse) => {
        const usersChatDto = chatResponse.usersChatDto;
        this.chatUserIdMap = chatResponse.chatIdUserId;
        this.privateChatNames = chatResponse.privateChatNames;
        console.log('Private chat names:', this.privateChatNames);
        Object.keys(usersChatDto).forEach((userIdToRetrieve) => {
          this.privateChatUsers.push(usersChatDto[userIdToRetrieve]);
        });
        this.userService.fetchProfilePictures(this.privateChatUsers);
      },
      (error) => {
        console.error('Error fetching private chats:', error);
      }
    );
  }

  onSearchChange(event: any) {
    const query = event.target.value;
    this.searchUsers(query);
  }
  

  fetchUserImage(email: any) {
    this.userService.getUserProfilePic(email).subscribe(
      (url: SafeUrl) => {
        this.loggedInUser.profilePicUrl = url;
      },
      (error) => {
        console.error('Error fetching profile picture:', error);
      }
    );
  }

  fetchConnectedUsers(userId: string): void {
    this.connectionService.getUserFriends(userId).subscribe(
      (friends: User[]) => {
        this.connectedUsers = friends;
  
        this.userService.fetchProfilePictures(this.connectedUsers);
      },
    );
  }

  isMessageSent(message: Message): boolean {
    return message.senderId === this.loggedInUser.id;
  }
  
  getKeyByValue(map: { [key: string]: string }, searchValue: string): string | null {
    for (const key of Object.keys(map)) {
      if (map[key] === searchValue) {
        return key;
      }
    }
    return null;
  }
  
  getSelectedChatMessages(): Message[] {
    return this.chatMessages[this.selectedChatId] || [];
  }
  

  formatDate(date: Date): string {
    return format(date, 'MMMM d, yyyy, h:mm:ss a');
  }
}






