import { Component, Input, OnInit } from '@angular/core';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { EventsService } from 'src/app/services/event-services/events.service';
import { RegistUsersService } from 'src/app/services/event-services/regist-users.service';
import { Event } from "../../Models/Event";

@Component({
  selector: 'app-event-delete',
  templateUrl: './event-delete.component.html',
  styleUrls: ['./event-delete.component.sass']
})
export class EventDeleteComponent implements OnInit {
  @Input()
  event?: Event;
  selectedEvent!: Event;


  ngOnInit(): void {
    this.getAllSpecifics();
  }

  constructor(
    private eventService: EventsService,
    private registerService: RegistUsersService
  ){}

  alleventSpecifics: EventSpecifics[] = [];

  getAllSpecifics(){
    this.registerService.getAllEventSpecifics().subscribe(
      data => {
        this.alleventSpecifics = data;
      }
    );
  }

  delete(selectedEvent: Event) {
    const eventId = selectedEvent.id;
    this.selectedEvent = selectedEvent;

    this.selectedEvent.eventSpecifics = [];
    
    this.eventService.updateDetails(selectedEvent.id, this.selectedEvent).subscribe(
      (data: any) => {
        console.log("Event updated:", data);
        this.deleteEventSpecifics(eventId);
      }
    );
    this.deleteEventSpecifics(selectedEvent.id);
  }

  deleteEventSpecifics(eventId: string) {
    for (const es of this.alleventSpecifics) {
      if(es.events?.id.toLowerCase().includes(eventId)){
         this.eventService.deleteDetails(es.id).subscribe(
          data => {
            console.log("from the foreach " + es.id);
          }
         )
        }
    }
    this.eventService.deleteDetails(this.selectedEvent.id).subscribe(
      () => {
        console.log("Event deleted:", this.selectedEvent.id);
      }
    );
  }

 /*
  delete(selectedEvent: Event) {
    const eventId = selectedEvent.id;
    this.selectedEvent = selectedEvent;

    this.eventService.updateDetails(eventId, this.selectedEvent).subscribe(
      (data: any) => {
        this.selectedEvent.eventSpecifics = [];
        console.log(data);
      }
    );
    this.alleventSpecifics.forEach(
      eventSpecifics => {
        if(eventSpecifics){
        if(eventSpecifics.event.id === eventId){
          this.eventService.deleteEventSpecifics(eventSpecifics.id);
        }
      }
      });
    


    this.eventService.deleteDetails(selectedEvent.id).subscribe(
      data => {
        console.log("Successfully deleted!");
      }
    );
  }
 */
}
