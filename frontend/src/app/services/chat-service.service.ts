import { Injectable } from '@angular/core';
import { Stomp, CompatClient} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatServiceService {

  private stompClient: any;

  constructor() {}

  connect(): void {
    const socket = new SockJS('/chat');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {});
  }

  sendMessage(roomId: string, message: string): void {
    this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify({ content: message }));
  }

  subscribeToChat(roomId: string): Observable<any> {
    return new Observable(observer => {
      this.stompClient.subscribe(`/topic/chat/${roomId}`, (message: { body: string; }) => {
        observer.next(JSON.parse(message.body));
      });
    });
  }
}
