import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RecommendationDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllRecommendations(){
    return this.http.get<any>("http://localhost:8080/api/v1/recommendations");
  }
  
  deleteRecommendation(recommendationId: string){
    return this.http.delete(`http://localhost:8080/api/v1/recommendations/${recommendationId}`);
  }

  updateRecommendation(recommendationId: string, updatedRecommendation: any){
    return this.http.patch(`http://localhost:8080/api/v1/recommendations/${recommendationId}`, updatedRecommendation);
  } 

  saveRecommendation(recommendation: any){
    return this.http.post(`http://localhost:8080/api/v1/recommendations`, recommendation);
  }

  listGivenRecommendations(recommendedUserId:string){
    return this.http.get<any>(`http://localhost:8080/api/v1/recommendations/${recommendedUserId}`);
  }

  listReceivedRecommendations(recommendedUserId:string){
    return this.http.get<any>(`http://localhost:8080/api/v1/recommendations/recommendedUser/${recommendedUserId}/received`);
  }


}
