import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Event } from 'src/app/Models/Event';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
import { EventsService } from 'src/app/services/event-services/events.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.sass']
})
export class EventFormComponent implements OnInit{
    constructor(
        private eventService: EventsService,
        private router: Router,
        private authService: AuthenticationService,
        private userService: UserDataService
    ){}


    ngOnInit(): void {
    this.eventModel.createdBy = this.authService.getUserFromLocalStorage().email;
    console.log(this.authService.getUserFromLocalStorage().email);
    }


    eventModel: Event = {
    id:'',
    createdBy: '',
    eventSpecifics:[],
    name: '',
    topic: '',
    description: '',
    imgUrl: '',
    maxParticipants: 1
  };

  @ViewChild('eventForm', { static: false }) eventForm!: NgForm;

  submitted = false;
  OnSelect(event: Event) {
    this.router.navigate(
      ['/event-specifics']);
  }

  onSubmit() {
    this.submitted = true;
  }

  newEvent(){
    console.log("Inside the newEventMeth : " + this.eventModel.createdBy);
    this.eventService.createEvent(this.eventModel).subscribe(
      (createdEvent: Event) => {
        this.eventModel.imgUrl = '';
        this.router.navigate(
          ['/event-specifics'],
          {
            queryParams: {
              refresh: 'true',
              eventId: createdEvent.id
            }
          }
          );
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

  validateMaxParticipants(){
    const maxParticipantsValue = this.eventModel.maxParticipants;
    if (maxParticipantsValue <= 1) {
    this.eventForm.controls['maxParticipants'].setErrors({ 'invalidValue': true });
     } else {
    this.eventForm.controls['maxParticipants'].setErrors(null);
   }
  }
}
