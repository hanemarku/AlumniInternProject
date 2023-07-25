import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InterestDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllInterests(){
    return this.http.get<any>("http://localhost:8080/api/v1/interests");
  }
  
  deleteInterest(interestId: string){
    return this.http.delete(`http://localhost:8080/api/v1/interests/${interestId}`);
  }

  updateInterest(interestId: string, updatedInterest: any){
    return this.http.patch(`http://localhost:8080/api/v1/interests/${interestId}`, updatedInterest);
  } 

  saveInterest(interest: any){
    return this.http.post(`http://localhost:8080/api/v1/interests`, interest);
  }

}
