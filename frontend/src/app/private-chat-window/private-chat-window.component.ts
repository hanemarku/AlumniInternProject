
import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, NgZone, OnInit, Pipe, Renderer2, ViewChild } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { UserDataService } from '../services/user-service/user-data.service';
import { ConnectionRequestService } from '../services/connection-request/connection-request.service';
import { forkJoin } from 'rxjs';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { format } from 'date-fns';
import { ChatResponse, ChatServiceService } from '../services/chat-service.service';
import { MessageService } from '../services/message.service';
import Swal from 'sweetalert2';

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePicUrl: string | SafeUrl;
  selected?: boolean;
}

export interface UserChatDTO {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  profilePicUrl: string | SafeUrl;
}

export interface ChatDTO {
  id: string
  admin: string;
  name: string;
  users: UserChatDTO[];
  type: string;
}


interface Message {
  senderId: string;
  message: string;
  time: Date;
}


@Component({
  selector: 'app-private-chat-window',
  templateUrl: './private-chat-window.component.html',
  styleUrls: ['./private-chat-window.component.sass']
})



export class PrivateChatWindowComponent implements OnInit, AfterViewInit{
  
  @ViewChild('chatContainer', { static: false }) chatContainer!: ElementRef;

  loggedInUser: any = JSON.parse(localStorage.getItem('user') || '{}');
  connectedUsers: User[] = [];
  showAllUsers = false;
  showAllGroups = false;
  searchResults: UserChatDTO[] = []; 
  searchGroupResults: UserChatDTO[] = [];
  searchUser: string = ''; 
  addToGroupSearch: string = '';
  filteredUsers: any[] = []; 
  privateChatUsers: UserChatDTO[] = [];
  groupChatUsers: UserChatDTO[] = [];
  groupChats: ChatDTO[] = [];
  selectedUser: UserChatDTO | null = null; 
  selectedGroup: ChatDTO | null = null;
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
  newGroupName: string = '';
  groupNameTaken = false;


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
    private renderer: Renderer2,
    private cdRef: ChangeDetectorRef, 
  ){}

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.scrollChatContainerToBottom();
    }, 100); 
  }

  ngOnInit(): void {
    this.fetchConnectedUsers(this.loggedInUser.id);
    console.log(this.connectedUsers)
    this.fetchUserImage(this.loggedInUser.email);
    this.fetchPrivateChats(this.loggedInUser.id);
    this.fetchGroupChats(this.loggedInUser.id);
    this.chatMessages = {};
    this.scrollChatContainerToBottom();
  }

  private scrollChatContainerToBottom(): void {
    try {
      const chatContainerElement = this.chatContainer.nativeElement;
      chatContainerElement.scrollTop = chatContainerElement.scrollHeight;
      this.cdRef.detectChanges();
    } catch (err) {
      console.error('Error scrolling chat container:', err);
    }
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
    this.scrollChatContainerToBottom();
  }

  showMessage(userId: string, message: Message): void {
    const chatId = this.selectedChatId;
    if (!this.chatMessages[chatId]) {
      this.chatMessages[chatId] = [];
      this.scrollChatContainerToBottom();
    }
    this.chatMessages[chatId].push(message);
    this.privateChatName = `${this.selectedUser?.firstname} ${this.selectedUser?.lastname}`;
    this.scrollChatContainerToBottom();
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

  searchToGroup(keyword: string): void {
    if (!keyword) {
      this.searchGroupResults = [];
      return;
    }
    keyword = keyword.toLowerCase();
    this.userService.searchInChat(keyword).subscribe(
      (results: UserChatDTO[]) => {
        const selectedGroup = this.groupChats.find(
          (group) => group.id === this.selectedChatId
        );
  
        if (selectedGroup) {
          this.searchGroupResults = results.filter((resultUser) =>
            !selectedGroup.users.some(
              (groupUser) => groupUser.id === resultUser.id
            )
          );
        } else {
          this.searchGroupResults = results;
        }
        this.userService.fetchProfilePictures(this.searchGroupResults);
        this.searchGroupResults = this.searchGroupResults.filter((resultUser) =>
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
    this.selectedGroup = null;
    this.selectedChatId = this.getKeyByValue(this.chatUserIdMap, user.id) || '';
    this.privateChatName = this.selectedUser.firstname + ' ' + this.selectedUser.lastname;
    this.disconnect();
    this.connect(this.loggedInUser.id, this.selectedChatId);
    this.getMessagesOfaChat(this.selectedChatId); 
  }

  selectGroup(group: ChatDTO): void {
    this.selectedGroup = group;
    this.selectedChatId = group.id;
    this.searchUser = '';
    this.selectedUser = null;
    this.privateChatName = this.selectedGroup.name;
    this.searchGroupResults = [];
    this.addToGroupSearch = '';
    this.disconnect();
    this.connect(this.loggedInUser.id, this.selectedChatId);
    this.getMessagesOfaChat(this.selectedChatId); 
  }

  addToPrivateChat(user: UserChatDTO): void {
    if (!this.privateChatUsers.some(u => u.id === user.id)) {
      this.privateChatUsers.push(user);
      this.selectUser(user);
      this.selectedChatId = this.getKeyByValue(this.chatUserIdMap, user.id) || '';
      this.newChat(user.id);
    }
  }

  addUserToGroupChat(user: UserChatDTO): void {
    if (this.selectedChatId) {
      const groupId = this.selectedChatId;
      const userId = user.id;
      this.searchGroupResults = [];
      this.addToGroupSearch = '';
  
      this.chatService.addUserToGroup(groupId, userId).subscribe(
        () => {
          console.log(`User ${userId} added to group ${groupId}`);
          const time = new Date();
          const message = `User ${user.firstname} ${user.lastname} has been added to the group.`;
          this.stompClient?.publish({
            destination: `/app/${groupId}`,
            body: JSON.stringify({
              'senderId': this.loggedInUser.id,
              'message': message,
              'time': time,
            }),
          });
        },
        (error) => {
          console.error('Error adding user to group chat:', error);
        }
      );
    }
  }

  addToGroupChat(group: ChatDTO): void {
    if (!this.groupChats.some(g => g.id === group.id)) {
      if (!this.userAlreadyInGroup(this.loggedInUser.id)) {
        this.groupChats.push(group);
        this.selectGroup(group);
        this.selectedChatId = group.id;
        this.searchGroupResults = [];
        this.newChat(group.id);
      } else {
        console.log('User already in group.');
      }
    }
  }
  
  
  createNewGroup(): void {
    const isGroupNameTaken = this.groupChats.some((group) => group.name === this.newGroupName);
    
    if (isGroupNameTaken) {
      Swal.fire({
        icon: 'error',
        title: 'Group Name Taken',
        text: 'The group name is already taken. Please choose another name.',
      });
      return;
    }
    this.chatService.createGroupChat(this.loggedInUser.id, this.newGroupName).subscribe(
      (response: any) => {
        this.addToGroupChat({ id: response.chatId, admin: this.loggedInUser.id, name: this.newGroupName, users: [], type: 'GROUP' });
        this.groupNameTaken = false;
 
      },
      (error) => {
        console.error('Error creating chat:', error);
      }
    );
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

  getMessagesOfaChat(chatId: string): void {
    this.messageService.getMessagesOfChat(chatId).subscribe(
      (messages: Message[]) => {
        this.chatMessages[chatId] = messages;
        console.log('Messages:', messages);
        this.scrollChatContainerToBottom();
      },
      (error) => {
        console.error('Error fetching messages:', error);
      }
    );
  }


  userAlreadyInGroup(userId: string): boolean {
    return !!this.selectedGroup && this.selectedGroup.users.some(user => user.id === userId);
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

  fetchGroupChats(userId: string): void {
    this.chatService.getUserChatInfo(userId).subscribe(
      (chatResponse: ChatResponse) => {
        console.log('Chat response:', chatResponse);
        this.groupChats = chatResponse.groupChats;
        console.log('Group chats:', this.groupChats);
      },
      (error) => {
        console.error('Error fetching group chats:', error);
      }
    );
  }

  onSearchChange(event: any) {
    const query = event.target.value;
    this.searchUsers(query);
  }
  
  onSearchUsersGroupChange(event: any) {
    const query = event.target.value;
    this.searchToGroup(query);
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

  isUserAdminOfSelectedGroup(): boolean {
    return this.selectedGroup?.admin === this.loggedInUser.id;
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

  getSenderName(senderId: string): string {
    const sender = this.connectedUsers.find(user => user.id === senderId);
    if (sender) {
      return `${sender.firstName} ${sender.lastName}`;
    } else {
      return 'Unknown User';
    }
  }
  
}






