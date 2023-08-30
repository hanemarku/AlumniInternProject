import { Component, Input } from '@angular/core';
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
  registered = true;
  onRegister(){
    this.registered = !this.registered;
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
  
  constructor(
    private eventsService: EventsService
  ) {
  }
}
