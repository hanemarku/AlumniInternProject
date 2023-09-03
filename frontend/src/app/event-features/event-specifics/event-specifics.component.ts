import { Component, OnInit } from '@angular/core';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { CityList } from 'src/app/city/city.component';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-specifics',
  templateUrl: './event-specifics.component.html',
  styleUrls: ['./event-specifics.component.sass']
})
export class EventSpecificsComponent implements OnInit{


  ngOnInit(): void {}

  constructor(
    private eventService: EventsService,
    private citiesService: CityDataService
  ){}
  
    eventSpecificsModel: EventSpecifics = {
      date: new Date,
      events: [],
      cities: [],
      userEvents: []
  };

  cities: CityList[] = [];
  
  listCities(){
    this.citiesService.listCities().subscribe(
      data => {
       // this.cities.push(data);
       this.cities = data;
        console.log("Data:" + data);
      }
    )
    for(const c of this.cities ){
    console.log("Cities:" + c.name);
    }
  }

  submitted = false;
  onSubmit() {
    this.submitted = true;
  }

  addSpecifics(){
    this.eventService.saveEventSpecifics(this.eventSpecificsModel).subscribe();
  }
}
