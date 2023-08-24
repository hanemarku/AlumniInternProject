
import { Component, NgZone, OnInit } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { UserDataService } from '../services/user-service/user-data.service';
import { ConnectionRequestService } from '../services/connection-request/connection-request.service';
import { forkJoin } from 'rxjs';
import { Stomp, CompatClient} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { format } from 'date-fns';
// import { DatePipe } from '@angular/common';



export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePicUrl: string | SafeUrl;
}


@Component({
  selector: 'app-private-chat-window',
  templateUrl: './private-chat-window.component.html',
  styleUrls: ['./private-chat-window.component.sass']
})

export class PrivateChatWindowComponent implements OnInit{

  connectedUsers: User[] = []; 
  userId: string = JSON.parse(localStorage.getItem('user') || '{}').id;
  loggedInUser: any = JSON.parse(localStorage.getItem('user') || '{}');
  public stompClient!: CompatClient;
  public privateChats: Map<string, any[]> = new Map();
  public publicChats: any[] = [];
  public tab: string = "CHATROOM";
  public userData: any = {
    senderId: '',
    senderFullName: '',
    receiverId: '',
    connected: false,
    content: ''
  };

  selectedUserId: string = '';
  selectedUserName: string = '';

  constructor(
    private userService: UserDataService,
    private connectionService: ConnectionRequestService,
    private zone: NgZone,

  ){}

  ngOnInit(): void {
    this.fetchConnectedUsers(this.userId);
    this.fetchUserImage(this.loggedInUser.email);
    console.log(this.publicChats);

  }

  setTab(tabName: string) {
    this.tab = tabName;
  }

  connect() {
    const sock = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(sock);
    this.stompClient.connect({}, this.onConnected.bind(this), this.onError.bind(this));
  }

  onConnected() {
    this.zone.run(() => {
      this.userData.connected = true;
      this.stompClient.subscribe('/chatroom/public', this.onMessageReceived.bind(this));
      this.stompClient.subscribe('/user/' + this.userData.senderId + '/private', this.onPrivateMessage.bind(this));
      this.userJoin();
    });
  }

  userJoin() {
    const chatMessage = {
      senderId: this.selectedUserId,
      status: "JOIN"
    };
    this.stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
  }

  onMessageReceived(payload: any) {
    const payloadData = JSON.parse(payload.body);
    console.log(payloadData);
    console.log("status " + payloadData.status)
    switch (payloadData.status) {
      case "JOIN":
        if (!this.privateChats.get(payloadData.senderId)) {
          this.privateChats.set(payloadData.senderId, []);
        }
        break;
      case "MESSAGE":
        this.userData.senderFullName = this.getSenderFullName(payloadData.senderId);
        this.publicChats.push(payloadData);
        console.log(this.publicChats);
        break;
    }
  }

  onError(err: any) {
    console.log(err);
  }

  onPrivateMessage(payload: any) {
    const payloadData = JSON.parse(payload.body);
    if (this.privateChats && payloadData.senderId) {
      const senderId = payloadData.senderId;
      const privateChats = this.privateChats.get(senderId) || []; 
      privateChats.push(payloadData);
      this.privateChats.set(senderId, privateChats);
    }
  }
  
  registerUser(user: User) {
    this.selectedUserId = user.id;
    this.selectedUserName = `${user.firstName} ${user.lastName}`;
    this.connect();
  }
  

  handleMessage(event: any) {
    const { value } = event.target;
    this.userData.content = value;
  }


  sendValue() {
    if (this.stompClient && this.selectedUserName) {
      const chatMessage = {
        senderId: this.loggedInUser.id,
        receiverId: this.selectedUserId, 
        content: this.userData.content,
        timestamp: new Date().toISOString(), 
        status: "MESSAGE"
      };
  
      this.stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
      this.userData.content = "";
    }
  }


  handleUsername(event: any) {
    const { value } = event.target;
    this.userData.senderFullName = value;
  }

  getSenderFullName(senderId: string): string {
    const sender = this.connectedUsers.find(user => user.id === senderId);
    return sender ? `${sender.firstName} ${sender.lastName}` : 'Unknown Sender';
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

  formatDate(date: Date): string {
    return format(date, 'MMMM d, yyyy, h:mm:ss a');
  }
}






