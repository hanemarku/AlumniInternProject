import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserList } from 'src/app/user/list-users/list-users.component';

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
  educations: string[];
  profilePicUrl: string;
  phoneNumber: string;
  bio: string;
}

export interface UserTest {
  firstname: string;
  lastname: string;
  email: string;
  password: string;
  birthday: Date;
  bio: string;
  phoneNumber: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllUsers(){
    return this.http.get<UserList[]>("http://localhost:8080/api/v1/users");
  }

  updateEnabledStatus(id: string, status: boolean): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/v1/users/${id}/enabled/${status}`);
  }
  

  deleteUser(userId: string): Observable<any> {
    const url = `http://localhost:8080/api/v1/users/${userId}`;
    return this.http.delete(url);
  }


  private apiUrl = 'http://localhost:8080/api/v1/users';

  uploadFile(fileData: FormData): Observable<any> {
    return this.http.post(this.apiUrl + '/upload', fileData, {responseType: 'text'});
  }
  
  createUser(userData: User): Observable<any> {
    return this.http.post(this.apiUrl + '/signup', userData);
  }

  checkEmailAvailability(email: string): Observable<any> {
    return this.http.get(this.apiUrl + `/check-email?email=${email}`);
  }
  
  
}

  
  // createUser(formData: FormData): Observable<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json');
  //   return this.http.post(this.apiUrl, JSON.stringify(formData), { headers });
  // }
  
  

