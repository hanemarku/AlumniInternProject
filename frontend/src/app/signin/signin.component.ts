// signin.component.ts

import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.sass']
})
export class SigninComponent {
  constructor(private http: HttpClient) {}

  onSignIn(email: string, password: string) {
    const user = { email, password };
    this.http.post<any>('http://localhost:8080/api/v1/users/signin', user, { observe: 'response' })
      .subscribe(
        response => {
          // Get the 'jwt-token' header if it exists
          const jwtTokenHeader = response.headers.get('jwt-token');
          if (jwtTokenHeader !== null) {
            const token = jwtTokenHeader;
            if (token) {
              // Save the token to local storage
              localStorage.setItem('jwtToken', token);
              // Redirect the user to another page or do other actions
              // For example:
              // this.router.navigate(['/dashboard']);
            } else {
              console.error('JWT token not found in the response headers.');
            }
          } else {
            console.error('JWT token header not found in the response.');
          }
        },
        error => {
          console.error('Sign in failed:', error);
        }
      );
  }
}
