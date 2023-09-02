import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.sass']
})
export class ResetPasswordComponent {
  password: string = '';
  confirmPassword: string = '';

  constructor(
    private notificationService: NotificationService,
    private userService: UserDataService,
    private route: ActivatedRoute,
    private router: Router,
    ) {}

  submitForm(): void {
    if (this.password !== this.confirmPassword) {
      this.notificationService.notify(NotificationType.ERROR, 'Password does not match');
      return;
    }

    if (this.password.length < 8) {
      this.notificationService.notify(NotificationType.ERROR, 'Password must be at least 8 characters long');
      return;
    }


    this.route.queryParamMap.subscribe(params => {
      const token = params.get('token');
      console.log("token " + token);
      if (token) {
        this.userService.resetPassword(token, this.password).subscribe(
          response => {
            console.log(response);
            this.notificationService.notify(NotificationType.SUCCESS, 'Password reset successful');
            this.router.navigate(['/signin']);
          },
          error => {
            console.error('Error resetting password:', error);
          }
        );
      } else {
        console.log('No token provided');
      }
  });
 }
}
