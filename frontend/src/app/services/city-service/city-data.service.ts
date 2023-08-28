import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class CityDataService {

  private BASE_URL: string = 'http://localhost:8080/api/v1/cities';

  constructor(
    private http: HttpClient
  ) { }

  listCities(){
    return this.http.get<any>(this.BASE_URL);
  }

  saveCity(city: any){
    return this.http.post(`http://localhost:8080/api/v1/cities`, city);
    
  }

  deleteCity(cityId: string){
    return this.http.delete(`http://localhost:8080/api/v1/cities/${cityId}`);
  }

  updateCity(cityId: string, updatedCity: any){
    return this.http.patch(`http://localhost:8080/api/v1/cities/${cityId}`, updatedCity);
  }
  
  getCitiesByCountry(countryId: string){
    return this.http.get<any>(`http://localhost:8080/api/v1/cities/countries/${countryId}`);
  }

}
