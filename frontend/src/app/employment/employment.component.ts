import { Component, OnInit, ViewChild, EventEmitter, Input, Output } from '@angular/core';
import { Employment } from '../services/employment-service/employment-data.service';
import { FormBuilder } from '@angular/forms';
import { CountryList } from '../country/country.component';
import { CityList } from '../city/city.component';
import { CountryCitySelectorComponent } from '../country-city-selector/country-city-selector.component';
import { CountryDataService } from '../services/country-service/country-data.service';
import { Skill } from '../skill-search/skill-search.component';

@Component({
  selector: 'app-employment',
  templateUrl: './employment.component.html',
  styleUrls: ['./employment.component.sass']
})
export class EmploymentComponent implements OnInit{

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
  selectedSkills: Skill[] = [];

  // @Input() selectedSkills: Skill[] = [];
  @Input() selectedCityValue: string | null = null;

  @Output() selectedEmploymentChange: EventEmitter<Employment[]> = new EventEmitter();
  @Output() selectedSkillsChange: EventEmitter<Skill[]> = new EventEmitter();

  @ViewChild('countryCitySelector', { static: false }) countryCitySelector!: CountryCitySelectorComponent;


  constructor(
    private formBuilder: FormBuilder,
    private countryDataService: CountryDataService,
  ) {}

  ngOnInit(): void {
    this.countryDataService.listAllCountries().subscribe(
     (response: CountryList[]) => (this.countries = response)
   );

   this.selectedCity = '';
  }

  addEmployment() {
    if(this.newEmployment.companyName && this.newEmployment.mainActivities && this.newEmployment.occupationPosition){
      console.log('test');
      console.log('Selected City in employment:', this.selectedCityValue + " electedCity: " + this.selectedCity);
      console.log('Selected skills test :', this.selectedSkills);
      this.newEmployment.city = this.selectedCityValue;
      this.newEmployment.country = this.selectedCountry;
      this.newEmployment.skills = this.selectedSkills;
      if (this.newEmployment.ongoing) {
        this.newEmployment.toDate = null;
      }
      this.employments.push({...this.newEmployment});
      console.log('Employment: ', this.newEmployment);

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
    console.log('Selected Country in employemt :', this.selectedCountry);
  }

  onSelectedCityChange(selectedCity: string | null) {
    this.selectedCityValue = selectedCity;
    this.selectedCity = selectedCity;
  }

  onOngoingChange(event: any) {
    this.newEmployment.ongoing = event.target.checked;
  }

  getCountryName(employment: Employment): string {
    if (employment.country && employment.country.name) {
      return employment.country.name;
    } else {
      return 'Unknown Country';
    }
  }

  getSkillName(skill: Skill): string {
    if (skill.name) {
      return skill.name;
    } else {
      return 'Unknown Skill';
    }
  }

  // onSelectedSkillsChange(selectedSkills: Skill[]) {
  //   console.log('Selected Skills in employment: ', selectedSkills);
  //   this.selectedSkills = selectedSkills;
  //   this.selectedSkillsChange.emit(selectedSkills);
  // }

  onSelectedSkillsChange(selectedSkills: Skill[]) {
    this.selectedSkills = selectedSkills;
    console.log('Selected Skills:', this.selectedSkills);
   
  }


  //   onSelectedSkillsChange(selectedSkills: string[]) {
  //   this.selectedSkills = selectedSkills;
  //   console.log('Selected Skills:', this.selectedSkills);
    
  //   // this.msform.get('skills')?.setValue(selectedSkills);
  //   this.msform.patchValue({
  //     skills: this.selectedSkills,
  //   });
  // }

  
}
