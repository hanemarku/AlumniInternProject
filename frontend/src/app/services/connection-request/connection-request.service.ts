import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { Observable } from 'rxjs';

export interface UserDto {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  profilePicUrl: SafeUrl | string;
  followStatus: string;
}

export interface ConnectUserDTO {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  profilePicture: string;
}



@Injectable({
  providedIn: 'root'
})
export class ConnectionRequestService {

  private apiUrl = 'http://localhost:8080/api/v1/connectionRequest';

  constructor(private http: HttpClient) { }

  sendFollowRequest(senderId: string, receiverId: string): Observable<any> {
    const url = `${this.apiUrl}/${senderId}/sendConnectionRequest/${receiverId}`;
    return this.http.post(url, {});
  }

  getSentConnectionRequests(userId: string): Observable<any> {
    const url = `${this.apiUrl}/${userId}/myConnectionRequestSent`;
    return this.http.get(url);
  }

  getReceivedConnectionRequests(userId: string): Observable<any> {
    const url = `${this.apiUrl}/${userId}/myConnectionRequestReceived`;
    return this.http.get(url);
  }

  acceptConnectionRequest(requestId: string, receiverId: string): Observable<any> {
    const url = `${this.apiUrl}/${requestId}/acceptConnectionRequest/${receiverId}`;
    return this.http.put(url, null);
  }

  rejectConnectionRequest(requestId: string, receiverId: string): Observable<any> {
    const url = `${this.apiUrl}/${requestId}/rejectConnectionRequest/${receiverId}`;
    return this.http.put(url, null);
  }

  // getProfileSuggestions(userId: string): Observable<Map<ConnectUserDTO, number>> {
  //   const url = `${this.apiUrl}/suggestedAccounts/${userId}`;
  //   return this.http.get<Map<any, number>>(url);
  // }

  getProfileSuggestions(userId: string): Observable<any> {
    const url = `${this.apiUrl}/suggestedAccounts/${userId}`;
    return this.http.get(url);
  }

  getUserFriends(userId: string): Observable<any>{
    const url = `${this.apiUrl}/friends/${userId}`;
    return this.http.get(url);
  }


}
