import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
}
