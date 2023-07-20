import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserList } from 'src/app/user/list-users/list-users.component';


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
  
  
}
