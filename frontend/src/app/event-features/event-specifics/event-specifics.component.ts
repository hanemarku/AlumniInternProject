import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { CityList } from 'src/app/city/city.component';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-specifics',
  templateUrl: './event-specifics.component.html',
  styleUrls: ['./event-specifics.component.sass']
})
export class EventSpecificsComponent implements OnInit {
  
  constructor(
    private eventService: EventsService,
    private eventSpecificsService: EventSpecificsService,
    private citiesService: CityDataService,
    private authServices: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.listCities();
    this.listEvents();
    console.log(this.authServices.getUserFromLocalStorage().email);
  }

  @Input()
  event!: Event;

  cities: CityList[] = [];
  selectedCityId: string = '';
  events: Event[] = [];
  lastEvent: string = '';

  eventSpecificsModel: EventSpecifics = {
    id: '',
    date: '',
    events: this.event,
    city: null
  };

  listCities() {
    this.citiesService.listCities().subscribe(
      data => {
        this.cities = data;
        console.log("this.cities:" + data);
      }
    );
  }

  listEvents() {
    this.eventService.getEvents().subscribe(
      data => {
        this.events = data;
        console.log("this.events data:" + data);
      }
    );
  }

  showSuccessMessage = false;

  submitted = false;

  onSubmit() {
    this.submitted = true;
  }

  eventSpecificsList: EventSpecifics[] = [];

  addSpecifics() {
    this.submitted = true;
  
    const lastIndex = this.events.length - 1;
    const lastEventCreated = this.events[lastIndex];
  
    this.lastEvent = lastEventCreated.id;
    
    this.eventService.getEventsById(this.lastEvent).subscribe(
      eventData => {
        this.eventSpecificsModel.events = eventData;
        console.log(this.eventSpecificsModel.events.name);
        
        this.citiesService.getCityById(this.selectedCityId).subscribe(
          cityData => {
            this.eventSpecificsModel.city = cityData;
            console.log(this.eventSpecificsModel.city.name);
            this.eventSpecificsService.saveEventSpecifics(this.eventSpecificsModel).subscribe(
              (data) => {
                console.log("City ID: " + this.eventSpecificsModel.city!.name);
                console.log("Event ID: " + this.eventSpecificsModel.events!.id);
                this.eventSpecificsList = data;
                console.log(this.eventSpecificsList);
              }
            );
            this.showSuccessMessage = true;
            setTimeout(() => {
              this.showSuccessMessage = false;
            }, 5000);
          }
        );
      }
    );
  }
  
  getCurrentDate(): string {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    const day = currentDate.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

}
