import { Component, Input, OnInit } from '@angular/core';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
import { EventSpecificsService } from 'src/app/services/event-services/event-specifics.service';
import { EventsService } from 'src/app/services/event-services/events.service';
import { RegisterUsersService } from 'src/app/services/event-services/register-users.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';
import { UserEvents } from "../../Models/UserEvents";
import { MembershipRole } from "../../enum/membership-role.enum";
import { Status } from "../../enum/status.enum";
import { UserList } from "../../user/list-users/list-users.component";

@Component({
  selector: 'app-event-register',
  templateUrl: './event-register.component.html',
  styleUrls: ['./event-register.component.sass']
})
export class EventRegisterComponent implements OnInit{
  ngOnInit(): void {
    this.findTheUserLogged();
  }
  constructor(
    private eventService: EventsService,
    private eventSpecificsService: EventSpecificsService,
    private registerService: RegisterUsersService,
    private authService: AuthenticationService,
    private userDataService: UserDataService
  ){}

  @Input()
  eventSpecifics!: EventSpecifics;

  searchForEmail = this.authService.getUserFromLocalStorage().email;
  currentUser!: UserList;

  registUserEventModel: UserEvents ={
    id: '',
    user: this.currentUser,
    eventSpecifics: this.eventSpecifics,
    membershipRole: MembershipRole.Member,
    status: Status.PENDING
  }
  findTheUserLogged(){
    this.userDataService.getUserByEmail(this.searchForEmail).subscribe(
      (data: UserList) =>{
        this.currentUser = data;
      }
    );
  }

  clicked = false;

  registerUserToEvent(){
    this.clicked = !this.clicked;
    console.log('Button clicked' + 'Also the registered obj to be used' + this.registUserEventModel.status);
    this.registUserEventModel.user = this.currentUser;
    this.registUserEventModel.eventSpecifics = this.eventSpecifics;
    
    this.registerService.register(this.registUserEventModel).subscribe(
      (data: UserEvents) => {
        console.log('Registration successful:', data);
      },
      (error: any) => {
        console.error('Error during registration:', error);
      }
    );
  }
}
