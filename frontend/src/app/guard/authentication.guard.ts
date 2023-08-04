import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication-service/authentication.service';
import { NotificationService } from '../services/notification-service/notification.service';
import { NotificationType } from '../enum/notification-type.enum';
// import { NotificationService } from '../service/notification.service';

@Injectable({providedIn: 'root'})
export class AuthenticationGuard implements CanActivate {

  constructor(
    private authenticationService: AuthenticationService, 
    private router: Router,
    private notificationService: NotificationService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isUserLoggedIn();
  }

  private isUserLoggedIn(): boolean {
    if (this.authenticationService.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/signin']);
    this.notificationService.notify(NotificationType.ERROR, `You need to log in to access this page`.toUpperCase());
    return false;
  }

}

