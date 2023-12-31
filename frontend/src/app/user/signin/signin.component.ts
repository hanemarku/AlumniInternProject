// signin.component.ts

import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from 'src/app/services/authenication-service/authentication.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { OnDestroy, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { TokenType } from '@angular/compiler';
import { HeaderType } from 'src/app/enum/notification-type.enum';
import { Location } from '@angular/common';


@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.sass']
})
export class SigninComponent implements OnInit, OnDestroy {

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private notificationService: NotificationService,
    private location: Location

    ) {
      this.showLoading = false;
    }

  ngOnInit(): void {
    // this.notificationService.notify(NotificationType.SUCCESS, 'Welcome to the User Portal');
    if (this.authenticationService.isLoggedIn()) {
      console.log("User is logged in");
      this.router.navigateByUrl('/users');
    } else {
      console.log("User is not logged in");
      this.router.navigateByUrl('/signin');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  onSignIn(email: string, password: string) {
    this.showLoading = true;
    const user = { email, password };
    this.subscriptions.push(
    this.authenticationService.login(email, password)
      .subscribe(
        response => {
          const jwtTokenHeader = response.headers.get(HeaderType.JWT_TOKEN);
          const user = response.body;
          console.log("user is : " + user);
          console.log("is user enambled : " + user.enabled);
          if (jwtTokenHeader !== null) {
            this.authenticationService.saveToken(jwtTokenHeader);
            this.authenticationService.addUserToLocalStorage(user);
            if(user.enabled !== false){
              // this.router.navigateByUrl('/settings');
              this.showLoading = false;
              const userLoggedIn = this.authenticationService.getUserFromLocalStorage();
              this.notificationService.notify(NotificationType.SUCCESS, `Welcome ${userLoggedIn.firstname} ${userLoggedIn.lastname}`);
              this.router.navigateByUrl('/users');
              this.router.navigateByUrl('/users').then(() => {
                window.location.reload();
              });
            } else {
              this.authenticationService.logout();
              this.showLoading = false;
              this.notificationService.notify(NotificationType.ERROR, 'Please verify your account. If you have verified it but still cannot access your account, it may be disabled. Please contact your administrator.');
            }
          } else {
            this.showLoading = false;
            this.notificationService.notify(NotificationType.ERROR, 'An error occurred. Please try again.');
          }
       
        },
        error => {
          console.log(error.message);
          console.log("status" + error.status);
          this.sendErrorNotification(NotificationType.ERROR, error.status);
          this.showLoading = false;
        }
      )
    )
  }

  private sendErrorNotification(notificationType: NotificationType, message: number): void {
    if (message === 401) {
      this.notificationService.notify(notificationType, 'Invalid email or password');
    } else if (message === 404) {
      this.notificationService.notify(notificationType, 'User not found');
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }

  navigateToUsers(): void {
    this.router.navigate(['/users']).then(() => {
      window.location.reload();
    });
  }

  
}
