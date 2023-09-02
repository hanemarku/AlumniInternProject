import { Component, Input } from '@angular/core';
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
  @Input()
  event?: Event;
  selectedEvent?: Event;

  updated = true;
  onUpdateded(){
    this.updated = !this.updated;
    this.selectedEvent = this.event;
    if(this.selectedEvent){
      console.log(this.selectedEvent.id);
      return this.eventsService.getEventsById(this.selectedEvent.id).subscribe(
        data => {
          console.log(data);
        }
      );
    }
  return null;
  }

  deleted = true;

  onDeleted(){
    this.deleted = !this.deleted;
    this.selectedEvent = this.event;
    if(this.selectedEvent){
      console.log(this.selectedEvent.id);
      return this.eventsService.getEventsById(this.selectedEvent.id).subscribe(
        data => {
          console.log(data);
        }
      );
    }
  return null;
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

  constructor(
    private eventsService: EventsService,
    private eventSpecificService: EventSpecificsService
  ) {
  }
}
