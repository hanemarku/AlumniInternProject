import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CityList } from 'src/app/city/city.component';


@Injectable({
  providedIn: 'root'
})
export class CityDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllCities(){
    return this.http.get<any>("http://localhost:8080/api/v1/cities");
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
