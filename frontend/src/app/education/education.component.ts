import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CountryCitySelectorComponent } from '../country-city-selector/country-city-selector.component';
import { CountryList } from '../country/country.component';
import { CityList } from '../city/city.component';
import { CountryDataService } from 'src/app/services/country-service/country-data.service';
import { CityDataService } from '../services/city-service/city-data.service';
import { Education } from '../services/education-service/education-data.service';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.sass'],
})
export class EducationComponent implements OnInit {
 
  newEducation: Education = {
    institutionName: '',
    fieldOfQualification: '',
    fieldOfStudy: '',
    startDate: null,
    endDate: null,
    finalGrade: '',
    website: '',
    city: '',
    country: null,
  };
  

  educations: Education[] = [];
  countries: CountryList[] = [];
  cities: { [key: string]: CityList[] } = {};
  selectedCity: string | '' | null = '';
  selectedCountry: CountryList | null = null;

  @Input() selectedCityValue: string | null = null;
  @Output() selectedEducationChange: EventEmitter<Education[]> = new EventEmitter();
  @ViewChild('countryCitySelector', { static: false }) countryCitySelector!: CountryCitySelectorComponent;

  constructor(
    private formBuilder: FormBuilder,
    private countryDataService: CountryDataService,
    private cityDataService: CityDataService,

  ) {}

  ngOnInit() {
    this.countryDataService.listAllCountries().subscribe(
      (response: CountryList[]) => (this.countries = response)
    );

    this.selectedCity = '';
  }

  addEducation() {
    if(this.newEducation.fieldOfQualification && this.newEducation.fieldOfStudy && this.newEducation.institutionName){

      console.log('Selected City in education:', this.selectedCityValue + " electedCity: " + this.selectedCity)
      this.newEducation.city = this.selectedCityValue;
      this.newEducation.country = this.selectedCountry;
      
      this.educations.push({...this.newEducation});
      this.newEducation.institutionName = '';
      this.newEducation.fieldOfQualification = '';
      this.newEducation.fieldOfStudy = '';
      this.newEducation.startDate = null;
      this.newEducation.endDate = null;
      this.newEducation.finalGrade = '';
      this.newEducation.website = '';
      this.newEducation.city = '';
      this.selectedEducationChange.emit(this.educations);
    }

  }

  editEducation(index: number) {
    const selectedEducation = this.educations[index];
    this.newEducation = { ...selectedEducation };
    this.deleteEducation(index); 
  }

  onSelectedCountryChange(selectedCountry: CountryList | null) {
    this.selectedCountry = selectedCountry;
    console.log('Selected Country in education:', this.selectedCountry);
  }

  onSelectedCityChange(selectedCity: string | null) {
    this.selectedCityValue = selectedCity;
    this.selectedCity = selectedCity;
    console.log('Selected City in education:', this.selectedCityValue + " electedCity: " + this.selectedCity);
  }

  deleteEducation(index: number) {
    this.educations.splice(index, 1);
  }
}
