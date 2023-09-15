import { Component } from '@angular/core';
import { Event } from "../Models/Event";
import { EventsService } from "../services/event-services/events.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../services/authenication-service/authentication.service";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.sass']
})
export class EventComponent {
  selectedEvent?: Event;
  event: Event[] = [];
  hasAccess = false;
  theUserLogged! : string;
  constructor(
    private eventsService : EventsService,
    private router: Router,
    private authService: AuthenticationService
  ){

  }
  ngOnInit(): void {
    this.getEvents();
    this.theUserLogged = this.authService.getUserFromLocalStorage().email;
  }

  getEvents() {
    this.eventsService.getEvents().subscribe(
        e =>{
        this.event = e
        }
        );
  }

  OnSelect(event: Event) {
    this.selectedEvent = event;
    const eventId = this.selectedEvent?.id;
    this.router.navigate(['/event-detail', eventId]);
  }

  onManage(){
    this.hasAccess = true;
  }
}
