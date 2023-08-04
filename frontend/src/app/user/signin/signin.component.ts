import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/services/user-service/user-data.service';
import { AuthenticationService, UserLogin } from 'src/app/services/authentication-service/authentication.service';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { OnDestroy } from '@angular/core';
import { OnInit } from '@angular/core';
import { HeaderType } from 'src/app/enum/header-type.enum';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.sass']
})
export class SigninComponent implements OnInit, OnDestroy{

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];

  constructor(private router: Router, private authenticationService: AuthenticationService,
              private notificationService: NotificationService) {}
  
  ngOnInit(): void {
    if (this.authenticationService.isLoggedIn()) {
      console.log("User is logged in");
      this.router.navigateByUrl('/users');
    } else {
      console.log("User is not logged in");
      this.router.navigateByUrl('/signin');
    }
  }

  public onLogin(user: User): void {
    this.showLoading = true;
    console.log(user);
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe(
        (response: HttpResponse<any>) => {
          console.log(response);
          console.log("response headers " + response.headers);
          console.log("header keys", response.headers.keys());


  
          console.log(response.headers.get('jwt-token'));
        
          const token = response.headers.get('jwt-token');
          if (token) {
            this.authenticationService.saveToken(token);
          }
          if (response.body) {
            this.authenticationService.addUserToLocalStorage(response.body);
          }
      
          this.router.navigateByUrl('/users');
          this.showLoading = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
          this.showLoading = false;
        }
      )
    );
  }
  

  private sendErrorNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }


}
