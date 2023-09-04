import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { error } from 'jquery';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';
import { Event } from "../../Models/Event";
import { EventsService } from "../../services/event-services/events.service";

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.sass']
})
export class EventDetailComponent {

  constructor(
    private eventsService: EventsService,
    private eventSpecificService: EventSpecificsService,
    private router: Router
  ) {
  }
  @Input()
  event?: Event;
  selectedEvent?: Event;

  updated = true;
  onUpdateded(){
    this.updated = !this.updated;
    this.selectedEvent = this.event;
    const eventId = this.selectedEvent?.id;
  
    if (this.selectedEvent) {
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-edit', eventId]); // No colon in the route path
    }
  }

  deleted = true;
  onDeleted(){
    this.deleted = !this.deleted;
    this.selectedEvent = this.event;
    const eventId = this.selectedEvent?.id;
    if(this.selectedEvent){
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-delete',eventId])
    }
  }

  specifics = true;
  onSpecifics(){
    this.specifics = !this.specifics;
    this.selectedEvent = this.event;
    if(this.selectedEvent){
      console.log(this.selectedEvent.id);
      return this.eventSpecificService.getEventSpecificsByEventId(this.selectedEvent.id).subscribe(
        data => {
          console.log("event specifics data : " + data);
        }
      );
    }
    return error("NO EVENT SPECIFICS RETURNED");
  }

}
