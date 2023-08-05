import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from '../services/authenication-service/authentication.service';

@Injectable({ providedIn: 'root' })
export class NotLoggedInGuard implements CanActivate {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (!this.authenticationService.isLoggedIn()) {
      return true;
    } else {
      // User is already logged in, redirect to the error page
      this.router.navigate(['/error-page']);
      return false;
    }
  }
}
