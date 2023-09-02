import { Component, ElementRef, OnInit, AfterViewChecked } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Client, Message } from '@stomp/stompjs'; // Updated import for @stomp/stompjs
import * as SockJS from 'sockjs-client';
import { FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { UserDataService } from '../services/user-service/user-data.service';
import { AnyCatcher } from 'rxjs/internal/AnyCatcher';

export class User {
  id?: number;
  firstName?: string;
  nickname?: string;
  propic?: string;
}

export class Messaggio {
  ms_id?: number;
  sender?: string;
  t_stamp?: string;
  content?: string;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.sass']
})
export class ChatComponent implements OnInit, AfterViewChecked {
  url = 'http://localhost:8080';
  otherUser?: User;
  thisUser: User = JSON.parse(sessionStorage.getItem('user')!);
  channelName?: string;
  socket?: WebSocket;
  stompClient?: Client | any;
  newMessage = new FormControl('');
  messages?: Observable<Array<Messaggio>>;

  constructor(
    private route: ActivatedRoute,
    private userService: UserDataService,
    private http: HttpClient,
    private el: ElementRef
  ) {}

    ngOnInit(): void {
      const username = this.route.snapshot.paramMap.get('user');
      if (username) {
        this.userService.getUserByUsername(username).subscribe((data) => {
          this.otherUser = data;
          if (this.otherUser) {
            this.otherUser.propic = "data:image/jpeg;base64," + this.otherUser.propic;
            this.connectToChat();
            console.log(this.el);
            this.el.nativeElement.querySelector("#chat").scrollIntoView();
          } else {
            // Handle the case where this.otherUser is undefined
          }
        });
      } else {
        // Handle the case where username is undefined
      }
    }
    
  ngAfterViewChecked(): void {
    this.scrollDown();
  }

  scrollDown(){
    var container = this.el.nativeElement.querySelector("#chat");
    container.scrollTop = container.scrollHeight;
  }

  connectToChat() {
    const id1 = this.thisUser.id!;
    const nick1 = this.thisUser.nickname;
    const id2 = this.otherUser?.id!;
    const nick2 = this.otherUser?.nickname!;

    if (id1 > id2) {
      this.channelName = nick1 + '&' + nick2;
    } else {
      this.channelName = nick2 + '&' + nick1;
    }
    this.loadChat();
    this.stompClient = new Client();

    // Connect to the WebSocket server
    this.stompClient.webSocketFactory = () => new SockJS(this.url + '/chat');
    this.stompClient.activate();

    this.stompClient.onConnect = (frame: any) => {
      // The connection is established
      console.log('connected to: ' + frame);

      // Subscribe to the topic
      this.stompClient.subscribe('/topic/messages/' + this.channelName, (response: Message) => {
        console.log('response: ' + response.body);
        // Handle the received message
        this.loadChat();
      });
    };
  }

  sendMsg() {
    if (this.newMessage.value !== '') {
      this.stompClient!.send(
        '/app/chat/' + this.channelName,
        {},
        JSON.stringify({
          sender: this.thisUser.nickname,
          t_stamp: 'to be defined in server',
          content: this.newMessage.value,
        })
      );
      this.newMessage.setValue('');
    }
  }

  loadChat() {
    this.messages = this.http.post<Array<Messaggio>>(this.url + '/getMessages', this.channelName);
    this.messages.subscribe((data: Messaggio[]) => {
      let mgs: Array<Messaggio> = data;
      mgs.sort((a, b) => (a.ms_id || 0) > (b.ms_id || 0) ? 1 : -1);
      this.messages = of(mgs);
    });
    console.log(this.messages);
  }
  
  
  whenWasItPublished(myTimeStamp?: string) {
    if(!myTimeStamp) return;
    const endDate = myTimeStamp.indexOf('-');
    return (
      myTimeStamp.substring(0, endDate) +
      ' at ' +
      myTimeStamp.substring(endDate + 1)
    );
  }

}