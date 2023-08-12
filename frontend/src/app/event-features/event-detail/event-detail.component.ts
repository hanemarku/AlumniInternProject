import {Component, Input} from '@angular/core';
import {EventsService} from "../../services/event-services/events.service";
import {Event} from "../../Models/Event";

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.sass']
})
export class EventDetailComponent {
  constructor(
    private eventsService: EventsService
  ) {
  }

  @Input() event?: Event;

  save() {
    if(this.event){
      this.eventsService.updateDetails(this.event).subscribe();
    }
  }

}
