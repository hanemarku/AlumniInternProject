import { Component, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.sass']
})
export class EventFormComponent implements OnInit{

    eventModel: Event = {
    id:'',
    createdby:'',
    name: 'hardcoded',
    topic: 'hardcoded',
    description: 'hardcoded',
    imgUrl: 'hardcoded',
    maxParticipants: 0
  };
  
  submitted = false;

  onSubmit() {
    this.submitted = true;
  }

  newEvent(){
    this.eventService.createEvent(this.eventModel).subscribe();
  }

  constructor(
    private eventService: EventsService
  ){}

  ngOnInit(): void {
  }
}