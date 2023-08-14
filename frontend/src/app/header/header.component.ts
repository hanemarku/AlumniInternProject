import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authenication-service/authentication.service';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { UserDataService } from '../services/user-service/user-data.service';
import { Subscription } from 'rxjs/internal/Subscription';

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


export class HeaderComponent implements OnInit , OnDestroy{
  private profilePicSubscription: Subscription | undefined;
  
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

    if (this.authenticationService.isLoggedIn()) {
      console.log("User is logged in");
      this.loggedIn = true;
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
      
    } else {
      console.log("User is not logged in");
      this.loggedIn = false;
    }
  }

  signOut(): void {
    this.authenticationService.logout();
    this.loggedIn = false;
    this.profilePicUrl = '';
    this.userEmail = '';
    this.firstname = '';
    this.lastname = '';
  }
  
  ngOnDestroy(): void {
    if (this.profilePicSubscription) {
      this.profilePicSubscription.unsubscribe();
    }
  }

  
  
  
  
  

}
