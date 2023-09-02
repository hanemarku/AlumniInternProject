import { Component, NgZone, OnInit } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.sass']
})
export class RoomComponent  implements OnInit {
  public stompClient!: CompatClient;
  public privateChats: Map<string, any[]> = new Map();
  public publicChats: any[] = [];
  public tab: string = "CHATROOM";
  public userData: any = {
    username: '',
    receivername: '',
    connected: false,
    message: ''
  };

  constructor(
    private zone: NgZone
  ) { }

  ngOnInit(): void {
    // Initialize your component here.
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
      this.stompClient.subscribe('/user/' + this.userData.username + '/private', this.onPrivateMessage.bind(this));
      this.userJoin();
    });
  }
  

  userJoin() {
    const chatMessage = {
      senderName: this.userData.username,
      status: "JOIN"
    };
    this.stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
  }

  onMessageReceived(payload: any) {
    const payloadData = JSON.parse(payload.body);
    switch (payloadData.status) {
      case "JOIN":
        if (!this.privateChats.get(payloadData.senderName)) {
          this.privateChats.set(payloadData.senderName, []);
        }
        break;
      case "MESSAGE":
        this.publicChats.push(payloadData);
        break;
    }
  }

  // onPrivateMessage(payload: any) {
  //   const payloadData = JSON.parse(payload.body);
  //   if (this.privateChats.get(payloadData.senderName)) {
  //     this.privateChats.get(payloadData.senderName).push(payloadData);
  //   } else {
  //     const list = [];
  //     list.push(payloadData);
  //     this.privateChats.set(payloadData.senderName, list);
  //   }
  // }

  onPrivateMessage(payload: any) {
    const payloadData = JSON.parse(payload.body);
    if (this.privateChats && payloadData.senderName) {
      const senderName = payloadData.senderName; 
      const privateChats = this.privateChats.get(senderName) || []; 
      privateChats.push(payloadData);
      this.privateChats.set(senderName, privateChats);
    }
  }
  
  
  onError(err: any) {
    console.log(err);
  }

  handleMessage(event: any) {
    const { value } = event.target;
    this.userData.message = value;
  }

  sendValue() {
    if (this.stompClient) {
      const chatMessage = {
        senderName: this.userData.username,
        message: this.userData.message,
        status: "MESSAGE"
      };
      this.stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
      this.userData.message = "";
    }
  }

  sendPrivateValue() {
    if (this.stompClient) {
      const chatMessage = {
        senderName: this.userData.username,
        receiverName: this.tab,
        message: this.userData.message,
        status: "MESSAGE"
      };
  
      // Check if this.privateChats and this.tab are defined.
      if (this.privateChats && this.tab) {
        if (this.userData.username !== this.tab) {
          const tabChats = this.privateChats.get(this.tab);
          if (tabChats) {
            tabChats.push(chatMessage);
          } else {
            const list = [chatMessage];
            this.privateChats.set(this.tab, list);
          }
        }
      }
  
      this.stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
      this.userData.message = "";
    }
  }
  
  handleUsername(event: any) {
    const { value } = event.target;
    this.userData.username = value;
  }

  registerUser() {
    this.connect();
  }
}
