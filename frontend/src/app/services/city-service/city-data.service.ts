import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CityList } from 'src/app/city/city.component';

@Injectable({
  providedIn: 'root'
})
export class CityDataService {
  private apiUrl = 'http://localhost:8080/api/v1/cities';

  constructor(
    private http: HttpClient
  ) { }

  getCitiesByCountry(countryId: string): Observable<CityList[]>{
    const url = `${this.apiUrl}/list_by_country/${countryId}`;
    return this.http.get<CityList[]>(url);
  }

  listCities(): Observable<CityList[]>{
    return this.http.get<CityList[]>(this.apiUrl);
  }
 
}
