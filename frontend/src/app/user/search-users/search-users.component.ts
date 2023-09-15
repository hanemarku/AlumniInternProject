import {Component, OnInit} from '@angular/core';
import {UserDataService} from "../../services/user-service/user-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.sass']
})
export class SearchUsersComponent implements OnInit{
  ngOnInit(): void {
    this.getAllUsers();
  }
  constructor(
    private userDataService: UserDataService
  ) {
  }

  getAllUsers(){
    this.userDataService.getAllUsers().subscribe(
      data => {
        console.log(data);
      }
    );
  }

}
