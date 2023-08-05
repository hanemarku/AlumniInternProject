import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { AuthenticationService } from '../services/authenication-service/authentication.service';
import { NotificationService } from '../services/notification-service/notification.service';
import { NotificationType } from '../enum/header-type.enum';

@Injectable({ providedIn: 'root' })
export class AuthenticationGuard implements CanActivate {

  constructor(
    private authenticationService: AuthenticationService, 
    private router: Router,
    private notificationService: NotificationService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    return this.isUserLoggedIn();
  }

  private isUserLoggedIn(): boolean | UrlTree {
    if (this.authenticationService.isLoggedIn()) {
      return true;
    }
    this.notificationService.notify(NotificationType.ERROR, `You need to log in to access this page`.toUpperCase());
    return this.router.createUrlTree(['/signin']);
  }
}
