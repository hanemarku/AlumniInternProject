import { Component, Input, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.sass']
})
export class EventEditComponent  implements OnInit{

  @Input()
  event?: Event;
  selectedEvent?: Event;

  submitted = false;

  
  ngOnInit(): void {
  }
  
  constructor(
    private eventService: EventsService
  ){
  }

  onSubmit(){
    this.submitted = true;
  }
updateEvent() {
  if(this.event)
  this.eventService.updateDetails(this.event?.id, this.event).subscribe(
    (    data: any) => {
      console.log(data);
    }
    );
}

}
