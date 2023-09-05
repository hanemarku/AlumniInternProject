import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from "../../Models/Event";

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.sass']
})
export class EventDetailComponent {

  constructor(
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
      this.router.navigate(['/event-edit', eventId]);
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
    const eventId = this.selectedEvent?.id;
  
    if (this.selectedEvent) {
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-specifics-details', eventId]);
    }
  }

}
