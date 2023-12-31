import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable, catchError } from "rxjs";
import { EventSpecifics } from "../../Models/EventSpecifics";

@Injectable({
  providedIn: 'root'
})
export class EventSpecificsService {
  constructor(
    private http: HttpClient
  ) { }
  httpOptions = {
    headers:new HttpHeaders({'Content-Type': 'application/json'})
  }

  private BASE_URL: string = 'http://localhost:8080/api/v1/specifics';


  saveEventSpecifics(eventSpecifics: EventSpecifics): Observable<any>{
    return this.http.post(this.BASE_URL, eventSpecifics).pipe(
      catchError((error: EventSpecifics) => {
        console.error('Error saving event specifics:', error);
        throw error;
      })
    );
  }

  getEventSpecificsById(id: string): Observable<EventSpecifics>{
    const url = `${this.BASE_URL}/${id}`;
    return this.http.get<EventSpecifics>(url);
  }

  getEventSpecificsByEventId(eventId: string): Observable<EventSpecifics[]>{
    const url = `${this.BASE_URL}/event-specifics/${eventId}`;
    return this.http.get<EventSpecifics[]>(url);
  }

  getAllEventSpecifics(): Observable<EventSpecifics[]>{
    return this.http.get<EventSpecifics[]>(this.BASE_URL);
  }

  deleteEventSpecifics(id: string){
    const url = `${this.BASE_URL}/${id}`;
    return this.http.delete(url);
  }

  deleteEventSpecificsByEvent(id: string){
    const url = `${this.BASE_URL}/event/${id}`;
    return this.http.delete(url);
  }
}
