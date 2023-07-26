// country-city-selector.component.ts
import { Component, Input, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CityList } from 'src/app/city/city.component';
import { CountryList } from 'src/app/country/country.component';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-country-city-selector',
  templateUrl: './country-city-selector.component.html',
  styleUrls: ['./country-city-selector.component.sass'],
})
export class CountryCitySelectorComponent implements OnInit {
  @Input() countries: CountryList[] = [];
  @Input() cities: { [key: string]: CityList[] } = {};
  @Input() msform: FormGroup = new FormGroup({});

  @Output() selectedCountryChange: EventEmitter<CountryList | null> = new EventEmitter();
  @Output() selectedCityChange: EventEmitter<string | null> = new EventEmitter();



  selectedCountry: CountryList | undefined | null = null;
  selectedCityFromList = true;
  customCity: string | null = null;
  selectedCity: string | null = null;

  countryControl!: FormControl;
  cityControl!: FormControl;
  customCityControl!: FormControl;

  constructor(
    private formBuilder: FormBuilder,
    private cityDataService: CityDataService) {}

  ngOnInit() {
    this.countryControl = this.formBuilder.control('', Validators.required);
    this.cityControl = this.formBuilder.control('');
    this.customCityControl = this.formBuilder.control('');

    this.countryControl.valueChanges.subscribe(() => {
      this.onCountryChange();
    });

    this.cityControl.valueChanges.subscribe(() => {
      this.onCityInputChange();
    });
    
    this.customCityControl.valueChanges.subscribe((value) => {
      this.selectedCityChange.emit(value);
      console.log("selected city emit : " + value);
    });
    
  }

  // onCountryChange() {
  //   const selectedCountryValue = this.countryControl.value;
  //   // console.log("selected country value: " + selectedCountryValue);
  //   if (selectedCountryValue) {
  //     this.selectedCountry = selectedCountryValue;
  //   } else {
  //     this.selectedCountry = null;
  //   }
  //   if (selectedCountryValue) {
  //     this.cityDataService.getCitiesByCountry(selectedCountryValue).subscribe(
  //       (cities: CityList[]) => {
  //         this.cities[selectedCountryValue] = cities;
  //         // console.log("cities: " + cities);
  //       },
  //       (error) => {
  //         console.error(error);
  //       }
  //     );
  //   }
  //   this.updateCityValidity();
  //   console.log("selected city emit 2" + this.selectedCountry);
  //   this.selectedCountryChange.emit(this.selectedCountry);
  // }

  onCountryChange() {
    const selectedCountryValue = this.countryControl.value;
    if (selectedCountryValue) {
      const selectedCountryObject = this.countries.find(country => country.id === selectedCountryValue);
      this.selectedCountry = selectedCountryObject;
    } else {
      this.selectedCountry = null;
    }
    if (selectedCountryValue) {
      this.cityDataService.getCitiesByCountry(selectedCountryValue).subscribe(
        (cities: CityList[]) => {
          this.cities[selectedCountryValue] = cities;
        },
        (error) => {
          console.error(error);
        }
      );
    }
    this.updateCityValidity();
    console.log("selected country emit 2" + this.selectedCountry);
    this.selectedCountryChange.emit(this.selectedCountry);
  }
  

  updateCityValidity() {
    const cityControl = this.cityControl;
    if (this.selectedCityFromList) {
      cityControl?.setValidators([Validators.required]);
    } else {
      cityControl?.setValidators(null);
    }
    cityControl?.updateValueAndValidity();
  }

  onCityInputChange() {
    const selectedCityValue = this.cityControl.value !== undefined ? this.cityControl.value : null;
    this.selectedCityFromList = this.selectedCountry ? this.cities[this.selectedCountry.id]?.some(city => city.id === selectedCityValue) : false;

    if (this.selectedCityFromList && this.selectedCountry) {
      this.selectedCity= this.cities[this.selectedCountry.id].find(city => city.id === selectedCityValue)?.name || null;
      console.log("selected city from list name : " + this.selectedCity);
    } else {
      this.selectedCity = null;
    }
  
    this.selectedCityChange.emit(this.selectedCity);
  }



  submitCustomCity() {
    this.cityControl.setValue(this.customCity);
    this.selectedCityFromList = false;
    this.updateCityValidity();
    console.log("selected city emit 3" + this.customCity);

  }
}
