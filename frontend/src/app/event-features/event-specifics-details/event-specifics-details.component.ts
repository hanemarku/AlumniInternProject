import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';
import { EventsService } from 'src/app/services/event-services/events.service';
import {AuthenticationService} from "../../services/authenication-service/authentication.service";

@Component({
  selector: 'app-event-specifics-details',
  templateUrl: './event-specifics-details.component.html',
  styleUrls: ['./event-specifics-details.component.sass']
})
export class EventSpecificsDetailsComponent implements OnInit {
  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.paramMap.get('eventId');
    console.log("the event id" + eventId);
    this.theSelectedEventId = eventId;
    console.log(this.theSelectedEventId);
    if (eventId) {
      this.eventService.getEventsById(eventId).subscribe((data: Event) => {
        this.events = data;
      });
    }
    this.getEventById();
    if (this.theSelectedEventId) {
      this.getSpecificsByEvent();
      console.log(this.selectedEventSpec);
    }
  }

  constructor(
    private eventSpecificsService: EventSpecificsService,
    private eventService: EventsService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

;
  @Input()
  events!: Event;
  selectedEvent!: Event;
  theSelectedEventId!: string | null;

  eventSpecifics: EventSpecifics[] = [];
  selectedEventSpec!: EventSpecifics;

  searchForEmail = this.authService.getUserFromLocalStorage().email;

  revealRegistration = true;

  add = true;

  getEventById() {
    if (this.theSelectedEventId)
      this.eventService.getEventsById(this.theSelectedEventId).subscribe(
        data => {
          this.selectedEvent = data;
          console.log(data);
        }
      );
  }

  getSpecificsByEvent() {
    if (this.theSelectedEventId)
      this.eventSpecificsService.getEventSpecificsByEventId(this.theSelectedEventId).subscribe(
        data => {
          this.eventSpecifics = data;
        }
      );
  }

  OnSelect(e: EventSpecifics) {
    this.selectedEventSpec = e;
    this.revealRegistration = !this.revealRegistration;
    const eventSpecificsId = this.selectedEventSpec.id;

    if (eventSpecificsId) {
      console.log(eventSpecificsId);
      this.router.navigate(['/event-register', eventSpecificsId]);
    }
    console.log(this.selectedEvent?.name + " The selected event Spec");
  }

  onDeleted(e: EventSpecifics) {
    this.selectedEventSpec = e;
    this.eventSpecificsService.deleteEventSpecifics(e.id).subscribe(
      data => {
        console.log("The eventSpecific to be deleted: " + e.id);
      }
    );
    location.reload();
  }
  addSpecificsToEvent(){
    this.add = !this.add;
  }
  onAccess(): boolean {
    if (this.selectedEvent) {
      if (this.selectedEvent.createdBy === this.searchForEmail) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
}
