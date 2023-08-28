import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { EventsService } from 'src/app/services/event-services/events.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.sass']
})
export class EventFormComponent implements OnInit{
    constructor(
        private eventService: EventsService,
        private router: Router
    ){}

    ngOnInit(): void {
    }

    eventModel: Event = {
    id:'',
    createdby:'',
    eventSpecifics:[],
    name: 'hardcoded',
    topic: 'hardcoded',
    description: 'hardcoded',
    imgUrl: 'hardcoded',
    maxParticipants: 0
  };

  @ViewChild('eventForm', { static: false }) eventForm!: NgForm;

  submitted = false;

  idCreated!: string;

  OnSelect(event: Event) {
    this.router.navigate(['/event-specifics'],{ queryParams: { refresh: 'true' } });
  }

  onSubmit() {
    this.submitted = true;
  }

  newEvent(){
    this.eventService.createEvent(this.eventModel).subscribe(
      () => {
        this.eventModel.imgUrl = '';
      }
    );
  }
  fullUrl : string | undefined;
  onSelectFile(imgFile: any) {
    if(imgFile.target.files && imgFile.target.files.length > 0){
      const file = imgFile.target.files[0];
      const fileName = file.name;
      const reader = new FileReader();
      reader.readAsDataURL(imgFile.target.files[0]);
      reader.onload =(img:any) =>{
          this.fullUrl = img.target.result;
          this.eventModel.imgUrl = fileName;
      };
      reader.readAsDataURL(file);
    }
  }
}
