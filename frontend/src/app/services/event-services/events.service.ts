import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Event } from 'src/app/interfaces/event';

@Injectable({
  providedIn: 'root'
})
export class EventsService{
  
  private BASE_URL: string = 'http://localhost:8080/api/v1/events';

  httpOptions = {
    headers:new HttpHeaders({'Content-Type': 'application/json'})
  }
  
  constructor(
    private http: HttpClient
  ) { }

  getEvents(): Observable<Event[]>{
    return this.http.get<Event[]>(this.BASE_URL);
  }

  getEventsById(id: string): Observable<Event[]>{
    const url = `${this.BASE_URL}/${id}`;
    return this.http.get<Event[]>(url)
  }

  updateDetails(event: Event): Observable<any> {
    return this.http.patch(this.BASE_URL, event);
  }

  addEvent(event: Event): Observable<Event>{
    event.name = event.name;
    event.description = event.description;
    
    return this.http.post(this.BASE_URL,event);
  }

}
