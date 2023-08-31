import { Component, Input, OnInit } from '@angular/core';
import { Event } from 'src/app/Models/Event';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { UserEvents } from 'src/app/Models/UserEvents';
import { MembershipRole } from 'src/app/enum/membership-role.enum';
import { Status } from 'src/app/enum/status.enum';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
import { EventsService } from 'src/app/services/event-services/events.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';
import { UserList } from 'src/app/user/list-users/list-users.component';

@Component({
  selector: 'app-event-register',
  templateUrl: './event-register.component.html',
  styleUrls: ['./event-register.component.sass']
})
export class EventRegisterComponent implements OnInit{
  ngOnInit(): void {
    this.findUser();
  }

  constructor(
    private eventService: EventsService,
    private authService: AuthenticationService,
    private userDataService: UserDataService
  ){}
  
  @Input()
  eventSpecifics!: EventSpecifics;
  @Input()
  event?:Event;

  searchForEmail = this.authService.getUserFromLocalStorage().email;
  theCurrentLoggedUser!: string;
  User! : UserList;

  findUser(){
    this.userDataService.getUserByEmail(this.searchForEmail).subscribe(
      (data: UserList) => {
        this.theCurrentLoggedUser = data.id;
        this.User = data;
        console.log("Inside find user. Consoling data.id : " + data.id);
      }
    );
  }
  
  registerModel : UserEvents ={
    id:'',
    user: this.User,
    eventSpecifics: this.eventSpecifics,
    membershipRole: MembershipRole.Creator,
    status: Status.PENDING
  }
    
  register(){
    this.eventService.register(this.registerModel);
  }
}
