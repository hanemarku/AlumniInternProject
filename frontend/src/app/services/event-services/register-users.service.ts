import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEvents } from 'src/app/Models/UserEvents';
import { User } from '../user-service/user-data.service';

@Injectable({
  providedIn: 'root'
})
export class RegisterUsersService {

  constructor(
    private http:HttpClient
  ) { }
  httpOptions = {
    headers:new HttpHeaders({'Content-Type': 'application/json'})
  }

  private BASE_URL: string= 'http://localhost:8080/api/v1/eventsAndUsers';


  getUsersByEventId(eventId: string): Observable<User[]>{
    const url = `${this.BASE_URL}/events/${eventId}/users`;
    return this.http.get<User[]>(url);
  }

  register(toRegister: UserEvents): Observable<UserEvents>{
    return this.http.post<UserEvents>(this.BASE_URL, toRegister);
  }

  getUsersByStatus(status: string): Observable<User[]>{
    const url = `${this.BASE_URL}/${status}`;
    return this.http.get<User[]>(url);
  }

}
