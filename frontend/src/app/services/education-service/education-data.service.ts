import { Injectable } from '@angular/core';
import { CityList } from 'src/app/city/city.component';
import { CountryList } from 'src/app/country/country.component';



export interface Education {
  institutionName: string;
  fieldOfQualification: string;
  fieldOfStudy: string;
  startDate: Date | null;
  endDate: Date | null;
  finalGrade?: string;
  website?: string;
  city: string | null;
  country: CountryList | null;

}

@Injectable({
  providedIn: 'root'
})
export class EducationDataService {

  constructor() { }
}
