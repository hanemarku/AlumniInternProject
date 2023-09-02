import { Component, Input, OnInit } from '@angular/core';
import { error } from 'jquery';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';

@Component({
  selector: 'app-event-specifics-details',
  templateUrl: './event-specifics-details.component.html',
  styleUrls: ['./event-specifics-details.component.sass']
})
export class EventSpecificsDetailsComponent implements OnInit {
  ngOnInit(): void {
    if(this.events){
    this.getSpecificsByEvent(this.events);
    }
  }

  constructor(
    private eventSpecificsService: EventSpecificsService
  ){}

  selectedEvent?: Event;

  @Input()
  events?: Event;

  eventSpecifics: EventSpecifics[] = [];
  selectedEventSpec!: EventSpecifics;
  getSpecificsByEvent(events: Event){
    this.selectedEvent = events;
    const searchId : string = this.selectedEvent.id;
      if(this.selectedEvent)
      return this.eventSpecificsService.getEventSpecificsByEventId(searchId).subscribe(
        data =>{
          this.eventSpecifics = data;
        }
        );
      else
      return error("No Event Specifics found")
  }

  revealRegistration = true;
  OnSelect(e: EventSpecifics){
    this.selectedEventSpec = e;
    this.revealRegistration = !this.revealRegistration;
    console.log(this.selectedEvent?.name + " The selected event Spec");
/**
 *     this.eventSpecificsService.getEventSpecificsById(e.id).subscribe(
 *       (data: EventSpecifics) => {
 *           this.selectedEventSpec = data;
 *           console.log("On select Specifics clicked : " + e.id);
 *         }
 *     );
 **/
  }
}
