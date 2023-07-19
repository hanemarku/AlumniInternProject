import { Component, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

export class UserList{
  constructor(
   public id : string,
   public firstname: string,
   public lastname: string,
   public email: string,
   public enabled: boolean,
   public birthday: Date,
   public profilePicUrl: string,
   public phoneNumber: string,
   public city: string,
   public country: string,
   public bio: string,
   public skills: { name: string }[], 
   public interests: {  name: string }[], 
   public role: string

  ){}
}

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.sass']
})
export class ListUsersComponent implements OnInit{

  users: UserList[] = [];
  dtOptions: DataTables.Settings = {};
  selectedUser: UserList | null = null;

  constructor(
    private userService: UserDataService
  ){}

  ngOnInit(): void {
    this.userService.listAllUsers().subscribe(
      response => {
        console.log(response);
        this.users = response;
      }
    )
  }

}
