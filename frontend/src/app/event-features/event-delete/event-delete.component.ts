import { Component, Input, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { UserEvents } from 'src/app/Models/UserEvents';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';
import { EventsService } from 'src/app/services/event-services/events.service';
import { RegisterUsersService } from 'src/app/services/event-services/register-users.service';

@Component({
  selector: 'app-event-delete',
  templateUrl: './event-delete.component.html',
  styleUrls: ['./event-delete.component.sass']
})
export class EventDeleteComponent implements OnInit {

  constructor(
    private eventSpecificsService: EventSpecificsService,
    private eventService: EventsService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ){}

  @Input()
  event!: Event;

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.paramMap.get('eventId');
    console.log("the event id being taken:" + eventId)
    this.thePassedEventId = eventId;
    console.log("the event id being passed:" + this.thePassedEventId)

    if (eventId) {
      this.eventService.getEventsById(eventId).subscribe((data: Event) => {
        this.event = data;
      });
    }
    this.getEventById();
    this.getAllEventSpecifics();
  }

  thePassedEventId: string | null | undefined;
  theEvent!: Event;
  alleventSpecifics: EventSpecifics[] = [];
  allUsersRegistered: UserEvents[] = [];

  getEventById(){
    if(this.thePassedEventId)
    this.eventService.getEventsById(this.thePassedEventId).subscribe(
      data =>{
        this.theEvent = data;
        console.log(data);
      });
  }

  getAllEventSpecifics(){
    if(this.thePassedEventId)
    this.eventSpecificsService.getEventSpecificsByEventId(this.thePassedEventId).subscribe(
      data=>{
        this.alleventSpecifics = data;
        console.log(data);
      }
    );
  }

  delete() {
    console.log("Selected event " + this.theEvent.id);
        for(let i = 0; i < this.alleventSpecifics.length; i++){
          if(this.alleventSpecifics[i].events?.id === this.thePassedEventId){
            this.eventSpecificsService.deleteEventSpecifics(this.alleventSpecifics[i].id).subscribe(
              () => {
                console.log(i + " deleted");
              }
            )
          }
        }
        if(this.thePassedEventId)
        this.eventService.deleteDetails(this.thePassedEventId).subscribe();
  }

  redirect(){
    this.router.navigate(['/event'])
  }

}
