import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CountryList } from 'src/app/country/country.component';


@Injectable({
  providedIn: 'root'
})
export class CountryDataService {

  constructor(
    private http: HttpClient
  ) { }

  listAllCountries(){
    return this.http.get<any>("http://localhost:8080/api/v1/countries");
  }

  saveCountry(country: any){
    return this.http.post(`http://localhost:8080/api/v1/countries`, country);
  }

  deleteCountry(countryId: string){
    return this.http.delete(`http://localhost:8080/api/v1/countries/${countryId}`);
  }

  updateCountry(countryId: string, updatedCountry: any){
    return this.http.patch(`http://localhost:8080/api/v1/countries/${countryId}`, updatedCountry);
  } 


}
