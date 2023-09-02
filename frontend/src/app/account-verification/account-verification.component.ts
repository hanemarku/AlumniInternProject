import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-account-verification',
  templateUrl: './account-verification.component.html',
  styleUrls: ['./account-verification.component.sass']
})
export class AccountVerificationComponent {
  verificationCode = '';

  constructor(private http: HttpClient) {}

  verifyUser() {
    this.http.post('/api/v1/users/verify', { verificationCode: this.verificationCode }).subscribe(
      response => {
        console.log(response); 
      },
      error => {
        console.error(error); 
      }
    );
  }
}
