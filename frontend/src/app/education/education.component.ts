import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { NgbDateStruct, NgbInputDatepicker } from '@ng-bootstrap/ng-bootstrap';
import { CityList } from '../city/city.component';
import { CityDataService } from '../services/city-service/city-data.service';
import { CountryList } from '../country/country.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CountryDataService } from 'src/app/services/country-service/country-data.service';


export interface Education {
  id: number;
  institutionName: string;
  fieldOfQualification: string;
  filedOfStudy: string;
  startDate: Date;
  endDate: Date;
  finalGrade?: string;
  website?: string;
  city?: string;

}

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.sass'],
})
export class EducationComponent implements OnInit{

  msform!: FormGroup;

  educations: Education[] = [];
  countries: CountryList[] = [];
  selectedCountry: string | null = null;
  selectedCityFromList = true;
  cities: { [key: string]: CityList[] } = {};
  customCity: string | null = null;

  @Output() selectedEducationChange: EventEmitter<Education[]> = new EventEmitter();



  constructor(private formBuilder: FormBuilder,
    private countryDataService: CountryDataService,
    private cityDataService: CityDataService) {}


  ngOnInit() {
    this.countryDataService.listAllCountries().subscribe(
      (response: CountryList[]) => (this.countries = response) 
    );

  }


  addEducation() {
    this.educations.push({
      id: this.educations.length + 1,
      institutionName: '',
      fieldOfQualification: '',
      filedOfStudy: '',
      startDate: new Date(),
      endDate: new Date(),
      finalGrade: '',
      website: '',
      city: '',
    });
    this.selectedEducationChange.emit(this.educations);
  }



  editEducation(education: Education) {
    // Implement logic to edit an education entry
  }

  removeEducation(education: Education) {
    const index = this.educations.findIndex((edu) => edu.id === education.id);
    if (index !== -1) {
      this.educations.splice(index, 1);
    }
  }


  
}
