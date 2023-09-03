import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface ChatMessageDTO {
  senderId: string;
  message: string;
  time: Date;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080/api/v1/message'; 

  constructor(
    private http: HttpClient,
  ) { }


  newMessage(message: string, senderId: string, chatId: string): Observable<any> {
    const body = {
      message: message,
      senderId: senderId,
      chatId: chatId
    };
    return this.http.post(`${this.baseUrl}/newMessage`, body);
  }

  getMessagesOfChat(chatId: string): Observable<ChatMessageDTO[]> {
    const url = `${this.baseUrl}/getMessagesOfChat/${chatId}`;
    return this.http.get<ChatMessageDTO[]>(url);
  }
}
