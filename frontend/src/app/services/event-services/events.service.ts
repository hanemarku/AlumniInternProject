import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { UserEvents } from 'src/app/Models/UserEvents';
import { Event } from "../../Models/Event";
import { User } from '../user-service/user-data.service';

@Injectable({
  providedIn: 'root'
})
export class EventsService{
  constructor(
    private http: HttpClient
  ) { }
  httpOptions = {
    headers:new HttpHeaders({'Content-Type': 'application/json'})
  }


  private BASE_URL: string = 'http://localhost:8080/api/v1/events';
  private REGISTER_URL: string= '/api/v1/eventsAndUsers';

  getEvents(): Observable<Event[]>{
    return this.http.get<Event[]>(this.BASE_URL);
  }

  createEvent(event: Event): any{
    return this.http.post(this.BASE_URL,event)
  }
  
  getEventsById(id: string): Observable<Event> {
    const url = `${this.BASE_URL}/${id}`;
    return this.http.get<Event>(url).pipe(
      catchError(error => {
        console.error('Error fetching event by id:', error);
        return throwError(error);
      })
    );
  }
  updateDetails(id: string,event: Event): any {
    const url = `${this.BASE_URL}/${id}`;
    return this.http.patch(url, event);
  }

  deleteDetails(id: string){
    const url = `${this.BASE_URL}/${id}`;
    return this.http.delete(url);
  }

  getUsersByEventId(eventId: string): Observable<User[]>{
    const url = `${this.REGISTER_URL}/events/${eventId}/users`;
    return this.http.get<User[]>(url);
  }

  register(toRegister: UserEvents){
    this.http.post(this.REGISTER_URL, toRegister);
  }

  getUsersByStatus(status: string): Observable<User[]>{
    const url = `${this.REGISTER_URL}/${status}`;
    return this.http.get<User[]>(url);
  }
}
