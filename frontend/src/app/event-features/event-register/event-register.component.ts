import { Component, Input, OnInit } from '@angular/core';
import { EventSpecifics } from 'src/app/Models/EventSpecifics';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
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
    private registerService: RegisterUsersService,
    private authService: AuthenticationService,
    private userDataService: UserDataService
  ){}

  @Input()
  eventSpecifics!: EventSpecifics;

  searchForEmail = this.authService.getUserFromLocalStorage().email;
  theCurrentUserId!: string;
  currentUser!: UserList;

  registUserEventModel: UserEvents ={
    userId: this.theCurrentUserId,
    eventSpecificsId: this.eventSpecifics.id,
    membershipRole: MembershipRole.Member,
    status: Status.PENDING
  }

/*
  registUserEventModel: UserEvents ={
   // id: '',
    user: this.currentUser,
    eventSpecifics: this.eventSpecifics,
    membershipRole: MembershipRole.Member,
    status: Status.PENDING
  }
*/
  findTheUserLogged(){
    this.userDataService.getUserByEmail(this.searchForEmail).subscribe(
      (data: UserList) =>{
        this.currentUser = data;
      }
    );
    this.theCurrentUserId = this.currentUser.id;
  }

  registerUserToEvent(){
  console.log('Button clicked' + 'Also the registered obj to be used' + this.registUserEventModel.status);
  //this.registUserEventModel.userId = this.currentUser.id;
  //this.registUserEventModel.eventSpecificsId = this.eventSpecifics.id;
  
  this.registerService.register(this.registUserEventModel).subscribe(
    (data: UserEvents) => {
      console.log('Registration successful:', data);
    },
    (error) => {
      console.error('Error during registration:', error);
    }
  );
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
