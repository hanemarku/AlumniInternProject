import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Education } from '../education-service/education-data.service';
import { JwtHelperService } from '@auth0/angular-jwt';




export class UserLogin {
  username: string;
  password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}

export interface User{
  firstname: string;
  lastname: string;
  email: string;
  password: string;
  birthday: Date;
  city: string;
  country: string;
  skills: string[];
  interests: string[];
  educationHistories: Education[];
  profilePicUrl: string;
  phoneNumber: string;
  bio: string;
}


@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {
  private token: string;
  private loggedInEmail: string | null;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public apiUrl = 'http://localhost:8080/api/v1/users';


  public login(user: User): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.apiUrl + '/signin', user, { observe: 'response' });
  }
  

  uploadFile(fileData: FormData): Observable<any> {
    return this.http.post(this.apiUrl + '/upload', fileData, {responseType: 'text'});
  }
  
  createUser(userData: User): Observable<any> {
    return this.http.post(this.apiUrl + '/signup', userData);
  }

  public logout(): void {
    this.token = '';
    this.loggedInEmail = null;
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToLocalStorage(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserFromLocalStorage(): User {
    const userData = localStorage.getItem('user');
    return userData ? JSON.parse(userData) : null;
  }

  public loadToken(): void {
    const token = localStorage.getItem('token');
    if (token !== null) {
      this.token = token;
      console.log("Token is " + this.token);
    } else {
      console.log("Token not found in localStorage");
    }
  }
  public getToken(): string | null{
    return this.token;
  }

  public isLoggedIn(): boolean {
    this.loadToken();
    console.log(this.token)
    console.log(this.token !== null)
    console.log(this.token !== '')
    if (this.token != null && this.token !== '') {
      console.log(this.jwtHelper.decodeToken(this.token).sub)
      if (this.jwtHelper.decodeToken(this.token).sub != null && this.jwtHelper.decodeToken(this.token).sub !== '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInEmail = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        } else {
          this.logout();
          return false;
        }
      } else {
        this.logout(); 
        return false;
      }
    } else {
      this.logout(); 
      return false;
    }
  }
  
}
