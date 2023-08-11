import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SettingService } from '../services/setting-service/setting.service';
import { UserDataService } from '../services/user-service/user-data.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.sass']
})
export class MessageComponent {
  message: string = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserDataService,
    ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.message = params['message'];
    });
    this.checkToken();
  }


  checkToken(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    if (token) {
      console.log(token);
      this.userService.verifyEmail(token).subscribe(
        response => {
          console.log(response);
          const isActivated = response.isActivated; 
          if(isActivated){
            this.message = "Email verification successful - you can now login to your account <a href='/signin'>here</a>";
          } else {
            this.message = "Email verification failed";
          }
        },
        error => {
          console.error(error);
          this.message = "Email verification failed";
        }
      );
    } else {
      this.message = "No token provided";
      console.log('No token provided');
    }
  }
  
}
