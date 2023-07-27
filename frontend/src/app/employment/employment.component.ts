import { Component, OnInit, ViewChild, EventEmitter, Input, Output } from '@angular/core';
import { Employment } from '../services/employment-service/employment-data.service';
import { FormBuilder } from '@angular/forms';
import { CountryList } from '../country/country.component';
import { CityList } from '../city/city.component';
import { CountryCitySelectorComponent } from '../country-city-selector/country-city-selector.component';
import { CountryDataService } from '../services/country-service/country-data.service';

@Component({
  selector: 'app-employment',
  templateUrl: './employment.component.html',
  styleUrls: ['./employment.component.sass']
})
export class EmploymentComponent implements OnInit{

  
  ngOnInit(): void {
    this.countryDataService.listAllCountries().subscribe(
     (response: CountryList[]) => (this.countries = response)
   );

   this.selectedCity = '';
 }


  newEmployment: Employment = {
    mainActivities: '',
    occupationPosition: '',
    companyName: '',
    department: '',
    ongoing: false,
    fromDate: null,
    toDate: null,
    city: '',
    country: null,
    skills: [],
  };

  employments: Employment[] = [];
  countries: CountryList[] = [];
  cities: { [key: string]: CityList[] } = {};
  selectedCity: string | '' | null = '';
  selectedCountry: CountryList | null = null;

  @Input() selectedCityValue: string | null = null;
  @Output() selectedEmploymentChange: EventEmitter<Employment[]> = new EventEmitter();
  @ViewChild('countryCitySelector', { static: false }) countryCitySelector!: CountryCitySelectorComponent;


  constructor(
    private formBuilder: FormBuilder,
    private countryDataService: CountryDataService,
  ) {}

  addEmployment() {
    if(this.newEmployment.companyName && this.newEmployment.mainActivities && this.newEmployment.occupationPosition){
      console.log('Selected City in employment:', this.selectedCityValue + " electedCity: " + this.selectedCity)
      this.newEmployment.city = this.selectedCityValue;
      this.newEmployment.country = this.selectedCountry;

      this.employments.push(this.newEmployment);

      this.newEmployment.companyName = '';
      this.newEmployment.mainActivities = ''
      this.newEmployment.occupationPosition = '';
      this.newEmployment.department = '';
      this.newEmployment.skills = [];
      this.newEmployment.ongoing = false;
      this.newEmployment.fromDate = null;
      this.newEmployment.toDate = null;
      this.newEmployment.city = '';
      this.newEmployment.country = null;
      this.selectedCity = '';
      this.selectedCountry = null;
      this.selectedEmploymentChange.emit(this.employments);
    }
  }

  editEmployment(index: number) {
    const selectedEmployment = this.employments[index];
    this.newEmployment = {...selectedEmployment};
    this.employments.splice(index, 1);
  }

  removeEmployment(index: number) {
    this.employments.splice(index, 1);
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
  
}
