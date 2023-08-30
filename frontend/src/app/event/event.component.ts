import { Component } from '@angular/core';
import { Event } from "../Models/Event";
import { EventsService } from "../services/event-services/events.service";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.sass']
})
export class EventComponent {
  /*TODO: REGISTER USERS*/
  /*TODO: LIST BY CONFIRMATION STATUS*/
  /*TODO: ****** GET THE USER WHO IS LOGGED*/
  /*TODO: DELETE EVENT SPECIFICS NOT ONLY THE EVENT*/
  
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
    this.eventsService.getEvents()
      .subscribe(e => this.event = e);
  }
  OnSelect(event: Event) {
    this.selectedEvent = event;
    return this.eventsService.getEventsById(this.selectedEvent.id).subscribe();
  }
}
