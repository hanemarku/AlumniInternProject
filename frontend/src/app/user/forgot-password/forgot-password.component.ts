import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.sass']
})
export class ForgotPasswordComponent {
  email: string = '';

  constructor(
    private router: Router,
    private notificationService: NotificationService,
    private userService: UserDataService) {}

  submitForm(): void {

    this.userService.checkEmailExists(this.email).subscribe(
      response => {
        console.log(response);
        console.log(response.available);
        const emailExist = response.available;
        if (!emailExist) {
          this.notificationService.notify(NotificationType.ERROR, "An account with that email does not exist.");
        } else {
          this.sendForgotPasswordEmail(this.email);
        }
      },
      error => {
        console.error(error);
        this.notificationService.notify(NotificationType.ERROR, "A problem occurred .");
      }
    );
  }

  sendForgotPasswordEmail(email : string): void {
    this.userService.sendForgotPasswordEmail(this.email).subscribe(
      response => {
        console.log(response);
        console.log(response.message);
        const confirmationMessage = 'An email has been sent to ' + this.email + ' with further instructions.';
        this.router.navigate(['/message'], { queryParams: { message: confirmationMessage } });
      },
      error => {
        console.error(error);
        this.notificationService.notify(NotificationType.ERROR, "A problem occurred .");
      }
    );
  }

}
