import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserList } from 'src/app/user/list-users/list-users.component';
import { Education } from '../education-service/education-data.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { map } from 'rxjs/operators';
import { Employment } from '../employment-service/employment-data.service';
import { HttpParams } from '@angular/common/http';


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
  employmentHistories: Employment[];
  profilePicUrl: SafeUrl | string;
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
  private baseUrl = 'http://localhost:8080/api/v1/users';

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer,

  ) { }

  listAllUsers(){
    return this.http.get<UserList[]>("http://localhost:8080/api/v1/users");
  }

  getUserByEmail(email: string) {
    const params = new HttpParams().set('email', email); // Create query parameter
    return this.http.get<UserList>(`http://localhost:8080/api/v1/users/email`, { params });
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
  
  getUserProfilePic(email: string): Observable<SafeUrl> {
    const backendImageUrl = `${this.baseUrl}/get-profile-pic?email=${email}`;
    return this.http.get(backendImageUrl, { responseType: 'blob' }).pipe(
      map((blob: Blob) => {
        const objectURL = URL.createObjectURL(blob);
        return this.sanitizer.bypassSecurityTrustUrl(objectURL);
      })
    );
  }
  
}

  
  // createUser(formData: FormData): Observable<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json');
  //   return this.http.post(this.apiUrl, JSON.stringify(formData), { headers });
  // }
  
  

