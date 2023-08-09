import { Component, OnInit } from '@angular/core';
import { Event } from '../interfaces/event';
import { EventsService } from '../services/event-services/events.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.sass']
})
export class EventsComponent implements OnInit{
  
  selectedEvent?: Event;
  event: Event[] = [];
  
  constructor(
    private eventsService : EventsService
  ){

  }

  ngOnInit(): void {
    this.getEvents();
  }

  getEvents() {
    this.eventsService.getEvents().subscribe(e => this.event = e);
  }

  add(event: Event) {
    this.eventsService.addEvent(event);
  }

OnSelect(event: Event) {
  this.selectedEvent = event;
}
}