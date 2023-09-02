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

  registerUserToEvent(){
    this.registUserEventModel.user = this.currentUser;
    this.registUserEventModel.eventSpecifics = this.eventSpecifics;
    this.registerService.register(this.registUserEventModel);
  }
  /**
   *
  searchForEmail = this.authService.getUserFromLocalStorage().email;
  theCurrentLoggedUser!: string;
  User! : UserList;

  registerModel : UserEvents ={
    id:'',
    user: this.User,
    eventSpecifics: this.specifics,
    membershipRole: MembershipRole.Member,
    status: Status.PENDING
  }


  findUser(){
    this.userDataService.getUserByEmail(this.searchForEmail).subscribe(
      (data: UserList) => {
        this.theCurrentLoggedUser = data.id;
        this.User = data;
        console.log("Inside find user. Consoling data.id : " + data.id);
      }
    );
  }

  register(){
    this.eventService.register(this.registerModel);
  }
  */
}
