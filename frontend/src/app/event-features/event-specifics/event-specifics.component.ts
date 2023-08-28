import { Component, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { CityList } from 'src/app/city/city.component';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-specifics',
  templateUrl: './event-specifics.component.html',
  styleUrls: ['./event-specifics.component.sass']
})
export class EventSpecificsComponent implements OnInit {
/*TODO: CHECK WHY IT IS NOT ADDING THE EVENT IN DB EVENT THOUGH IS CORRECTLY CONNECTED*/
  constructor(
    private eventService: EventsService,
    private citiesService: CityDataService
  ) {}

  ngOnInit(): void {
    this.listCities();
    this.listEvents();
  }

  cities: CityList[] = [];
  selectedCityId: string = '';
  events: Event[] = [];
  lastEvent: string = '';

  eventSpecificsModel: EventSpecifics = {
    id: '',
    date: '',
    event: null,
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

  submitted = false;

  onSubmit() {
    this.submitted = true;
  }
  addSpecifics() {
    this.submitted = true;
  
    const lastIndex = this.events.length - 1;
    const lastEventCreated = this.events[lastIndex];
  
    this.lastEvent = lastEventCreated.id;
    
    this.eventService.getEventsById(this.lastEvent).subscribe(
      eventData => {
        this.eventSpecificsModel.event = eventData;
        console.log(this.eventSpecificsModel.event.name);
        this.citiesService.getCityById(this.selectedCityId).subscribe(
          cityData => {
            this.eventSpecificsModel.city = cityData;
            console.log(this.eventSpecificsModel.city.name);

            this.eventService.saveEventSpecifics(this.eventSpecificsModel).subscribe(
              () => {
                console.log("City ID: " + this.eventSpecificsModel.city!.name);
                console.log("Event ID: " + this.eventSpecificsModel.event!.id);
              }
            );
          }
        );
      }
    );
  }
  
}
