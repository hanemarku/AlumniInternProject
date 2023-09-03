import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Event } from "../../Models/Event";

@Injectable({
  providedIn: 'root'
})
export class EventsService{

  private BASE_URL: string = 'http://localhost:8080/api/v1/events';
  private SPECIFICS_URL: string = 'http://localhost:8080/api/v1/specifics';

  httpOptions = {
    headers:new HttpHeaders({'Content-Type': 'application/json'})
  }
  constructor(
    private http: HttpClient
  ) { }

  getEvents(): Observable<Event[]>{
    return this.http.get<Event[]>(this.BASE_URL);
  }

  createEvent(event: any): any{
    return this.http.post(this.BASE_URL, event);
  }

  getEventsById(id: string): Observable<Event>{
    const url = `${this.BASE_URL}/${id}`;
    return this.http.get<Event>(url);
  }

  updateDetails(id: string,event: Event): any {
    const url = `${this.BASE_URL}/${id}`;
    return this.http.patch(url, event);
  }

  deleteDetails(id: string){
    const url = `${this.BASE_URL}/${id}`;
    return this.http.delete(url);
  }

  saveEventSpecifics(eventSpecifics: any): any{
    this.http.post(this.SPECIFICS_URL, eventSpecifics);
  }
}
