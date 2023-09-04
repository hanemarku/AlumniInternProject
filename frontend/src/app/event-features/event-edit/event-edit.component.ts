import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.sass']
})
export class EventEditComponent  implements OnInit{

  @Input()
  event!: Event;
  selectedEvent!: Event;

  submitted = false;

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.paramMap.get('eventId');
    if (eventId) {
      this.eventService.getEventsById(eventId).subscribe((data: Event) => {
        this.event = data;
      });
    }
  }
  
  constructor(
    private eventService: EventsService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ){
  }

  onSubmit(){
    this.submitted = true;
  }
updateEvent() {
  if (this.event) {
    this.eventService.updateDetails(this.event.id, this.event).subscribe(
      (data: any) => {
        console.log(data);
      }
    );
  } else {
    console.error('Event is undefined.');
  }
}

  addSpecifics() {
    if (this.selectedEvent) {
      this.router.navigate(['/event-specifics']);
      return this.eventService.getEventsById(this.selectedEvent.id).subscribe();
    } else {
    return console.error('SelectedEvent is undefined.');
    }
  }

}
