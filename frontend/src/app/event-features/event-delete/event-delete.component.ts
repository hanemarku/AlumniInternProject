import { Component, Input, OnInit } from '@angular/core';
import { EventsService } from 'src/app/services/event-services/events.service';
import { Event } from "../../Models/Event";

@Component({
  selector: 'app-event-delete',
  templateUrl: './event-delete.component.html',
  styleUrls: ['./event-delete.component.sass']
})
export class EventDeleteComponent implements OnInit {
  @Input()
  event?: Event;
  selectedEvent?: Event;

  ngOnInit(): void {
  }

  constructor(
    private eventService: EventsService
  ){

  }

  delete() {
    if(this.event)
    this.eventService.deleteDetails(this.event.id).subscribe(
      (    data: any) => {
        console.log(data);
      }
      );  }

}
