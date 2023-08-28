import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { City } from 'src/app/city/city.component';


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


  getCityById(id: string): Observable<City>{
    const url =`${this.BASE_URL}/${id}`;
    return this.http.get<City>(url).pipe(
      catchError(error => {
        console.log('Error fetching city with this id:', error);
        return throwError(error);
      })
    )
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
