import { Injectable } from '@angular/core';
import { CountryList } from 'src/app/country/country.component';
import { Skill } from 'src/app/skill-search/skill-search.component';


export interface Employment {
  mainActivities: string;
  occupationPosition: string;
  companyName: string;
  department?: string;
  ongoing?: boolean;
  fromDate?: Date | null;
  toDate?: Date | null;
  city?: string | null;
  country?: CountryList | null;
  skills?: Skill[];
}


@Injectable({
  providedIn: 'root'
})
export class EmploymentDataService {

  constructor() { }
}
