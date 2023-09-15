import { Component, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-search-by',
  templateUrl: './event-search-by.component.html',
  styleUrls: ['./event-search-by.component.sass']
})
export class EventSearchByComponent implements OnInit {
  theEvents: Event[] = [];
  matchedEventId: string[] = [];
  filteredEvents: Event[] =[];
  theKeyword!: string;

  constructor(
    private eventService: EventsService
  ){}
  
  ngOnInit(): void {
    this.getAllEvents();
  }

  getAllEvents(): void{
    this.eventService.getEvents().subscribe(
      e => {
        this.theEvents = e;
      }
    );
  }
  clear(){
    location.reload();
  }
  
  search(): string[]{
    this.matchedEventId.length = 0;
    
    for (const event of this.theEvents) {
      if(event.name.toLowerCase().includes(this.theKeyword)
        || event.topic.toLowerCase().includes(this.theKeyword)
        || event.description.toLowerCase().includes(this.theKeyword)){
          this.matchedEventId.push(event.id.toString());
        }
    }
    console.log(this.matchedEventId);
    return this.matchedEventId;
  }
  eventsToDisplay(): Event[]{
    if(this.matchedEventId.length === 0){
      throw new Error("No events to display;")
    }
    for(const id of this.matchedEventId){
      this.eventService.getEventsById(id).subscribe(
        data => {
          this.filteredEvents.push(data);
          console.log(data);
        }
      )
    }
  return this.filteredEvents;
  }
}


