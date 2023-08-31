import { Component, Input, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { EventsService } from 'src/app/services/event-services/events.service';
import { RegistUsersService } from 'src/app/services/event-services/regist-users.service';

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
    this.selectedEvent = selectedEvent;
        for(let i = 0; i < this.alleventSpecifics.length; i++){
          if(this.alleventSpecifics[i].events?.id === selectedEvent.id){
            this.registerService.deleteEventSpecifics(this.alleventSpecifics[i].id).subscribe(
              () => {
                console.log(i + " deleted");
              }
            )
          }
          if(i === this.alleventSpecifics.length-1){
            console.log("inside the last i interation" + i);
            this.eventService.deleteDetails(selectedEvent.id).subscribe();
          }
        }
  }
}
