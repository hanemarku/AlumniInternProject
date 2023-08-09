import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../interfaces/event';
import { EventsService } from '../services/event-services/events.service';


@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.sass']
})
export class EventDetailComponent {

  constructor(
    private eventsService: EventsService,
    private route: ActivatedRoute,
    private location: Location
  ){

  }

  @Input() event?: Event;

  save() {
    if(this.event){
      this.eventsService.updateDetails(this.event).subscribe();
    }
  }
}
