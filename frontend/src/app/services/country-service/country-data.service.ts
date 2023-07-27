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
    return this.http.get<CountryList[]>("http://localhost:8080/api/v1/countries");
  }
}
