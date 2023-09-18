import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Event } from "../../Models/Event";
import {EventsService} from "../../services/event-services/events.service";
import {AuthenticationService} from "../../services/authenication-service/authentication.service";

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.sass']
})
export class EventDetailComponent implements OnInit{

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.paramMap.get('eventId');
    if (eventId) {
      this.eventService.getEventsById(eventId).subscribe((data: Event) => {
        this.event = data;
      });
    }
  }
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthenticationService,
    private eventService: EventsService
  ) {
  }
  @Input()
  event?: Event;
  selectedEvent?: Event;

  updated = true;
  onUpdateded(){
    this.updated = !this.updated;
    this.selectedEvent = this.event;
    const eventId = this.selectedEvent?.id;

    if (this.selectedEvent) {
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-edit', eventId]);
    }
  }

  deleted = true;
  onDeleted(){
    this.deleted = !this.deleted;
    this.selectedEvent = this.event;
    const eventId = this.selectedEvent?.id;
    if(this.selectedEvent){
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-delete',eventId])
    }
  }

  specifics = true;
  onSpecifics(){
    this.specifics = !this.specifics;
    this.selectedEvent = this.event;
    const eventId = this.selectedEvent?.id;

    if (this.selectedEvent) {
      console.log(this.selectedEvent.id);
      this.router.navigate(['/event-specifics-details', eventId]);
    }
  }

  searchForEmail = this.authService.getUserFromLocalStorage().email;
  onAccess(): boolean{
    this.selectedEvent = this.event;
    if(this.selectedEvent?.createdBy === this.searchForEmail){
      return true;
    }else {
      return false;
    }
  }

}
