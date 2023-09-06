import { Component } from '@angular/core';
import { Event } from "../Models/Event";
import { EventsService } from "../services/event-services/events.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.sass']
})
export class EventComponent {
  selectedEvent?: Event;
  event: Event[] = [];
  constructor(
    private eventsService : EventsService,
    private router: Router
  ){

  }
  ngOnInit(): void {
    this.getEvents();
  }

  getEvents() {
    this.eventsService.getEvents().subscribe(
        e =>{
        this.event = e
        }
        );
  }

  OnSelect(event: Event) {
    this.selectedEvent = event;
    const eventId = this.selectedEvent?.id;
    this.router.navigate(['/event-detail', eventId]);
  }
}
