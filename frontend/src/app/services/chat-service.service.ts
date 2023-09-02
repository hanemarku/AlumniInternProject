import { Injectable } from '@angular/core';
import { Stomp, CompatClient} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authenication-service/authentication.service';
import { UserChatDTO } from '../private-chat-window/private-chat-window.component';


export interface ChatDTO {
  id: string
  admin: string;
  type: string;
  name: string;
  users: UserChatDTO[];
}

export interface ChatResponse {
  privateChatNames: { [key: string]: string };
  usersChatDto: { [key: string]: UserChatDTO};
  groupChats: ChatDTO[];
  privateChats: ChatDTO[];
  chatIdUserId: { [key: string]: string}
}

@Injectable({
  providedIn: 'root'
})
export class ChatServiceService {
  

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService) {}



  private baseUrl = 'http://localhost:8080/api/v1/chat'; 

  createPrivateChat(id: string, data: any): Observable<string> {
    const url = `${this.baseUrl}/newchat?id=${id}&param1=${data.param1}`;
    return this.http.get<string>(url);
  }

  createGroupChat(id: string, name: string): Observable<any> {
    const url = `${this.baseUrl}/newgroupchat?id=${id}&name=${name}`;
    return this.http.get(url);
  }
  

  getUserChatInfo(userId: string): Observable<ChatResponse> {
    const url = `${this.baseUrl}/user_chats?id=${userId}`;
    return this.http.get<any>(url);
  }

  addUsersToGroup(groupId: string, userIds: string[]): Observable<any> {
    const userAdditionDTO = { 
      groupId: groupId, 
      userIds: userIds 
    };
    return this.http.post(`${this.baseUrl}/addUsersToChat`, userAdditionDTO);
  }
  
  addUserToGroup(groupId: string, userId: string): Observable<any> {
    const userAdditionDTO = { 
      groupId: groupId, 
      userId: userId
    };
    return this.http.post(`${this.baseUrl}/addUserToChat`, userAdditionDTO);
  }

  // createPrivateChat(id: string): Observable<string> {
  //   const jwtToken = this.authService.getToken();
  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${jwtToken}`
  //   });
  //   const httpOptions = {
  //     headers: headers
  //   };

  //   const url = `${this.baseUrl}/newchat?id=${id}`;
  //   return this.http.get<string>(url, httpOptions);
  // }

  
}
