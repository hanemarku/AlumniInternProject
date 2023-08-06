import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authenication-service/authentication.service';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { UserDataService } from '../services/user-service/user-data.service';

export class UserHeader{
  constructor(
   public firstname: string,
   public lastname: string,
   public email: string,
   public profilePicUrl: SafeUrl | string,

  ){}
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})


export class HeaderComponent implements OnInit{
  public loggedIn = false;
  profilePicUrl: SafeUrl | string = '';
  userEmail: string = ''; 
  firstname: string = '';
  lastname: string = '';

  constructor(
    private authenticationService: AuthenticationService,
    private http: HttpClient,
    private sanitizer: DomSanitizer,
    private userService: UserDataService,
  ) { }

  ngOnInit(): void {

    this.userEmail = JSON.parse(localStorage.getItem('user') || '{}').email;
    this.firstname = JSON.parse(localStorage.getItem('user') || '{}').firstname;
    this.lastname = JSON.parse(localStorage.getItem('user') || '{}').lastname;
    console.log("email + " + this.userEmail)
    var email = this.userEmail;

    this.userService.getUserProfilePic(email).subscribe(
      (url: SafeUrl) => {
        this.profilePicUrl = url;
      },
      (error) => {
        console.error('Error fetching profile picture:', error);
      }
    );
    

    if (this.authenticationService.isLoggedIn()) {
      console.log("User is logged in");
      this.loggedIn = true;
    } else {
      console.log("User is not logged in");
      this.loggedIn = false;
    }
  }


}
