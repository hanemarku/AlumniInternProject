import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserList } from 'src/app/user/list-users/list-users.component';
import { Education } from '../education-service/education-data.service';
import { Employment } from '../employment-service/employment-data.service';
import { UserDto } from '../connection-request/connection-request.service';


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
    const params = new HttpParams().set('email', email); 
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
  
  verifyEmail(token: string): Observable<any> {
    const url = `${this.baseUrl}/verify?token=${token}`;
    return this.http.get(url);
  }

  forgotEmail(token: string): Observable<any> {
    const url = `${this.baseUrl}/forgot-email?token=${token}`;
    return this.http.get(url);
  }

  checkEmailExists(email: string): Observable<any> {
    const url = `${this.baseUrl}/check-email-exists?email=${email}`;
    return this.http.get(url);
  }

  sendForgotPasswordEmail(email: string): Observable<any> {
    const url = `${this.apiUrl}/send-forgot-password-email?email=${email}`;
    return this.http.post<any>(url, {});
  }

  resetPassword(token: string , newPassword: string): Observable<any> {
    const url = `${this.apiUrl}/reset-password`;
    const resetPass = {token, newPassword }; 
    return this.http.post<any>(url, resetPass, { observe: 'response' });
  }

  getAllUsers(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(`${this.apiUrl}/all`);
  }

}


  
  // createUser(formData: FormData): Observable<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json');
  //   return this.http.post(this.apiUrl, JSON.stringify(formData), { headers });
  // }
  
  

