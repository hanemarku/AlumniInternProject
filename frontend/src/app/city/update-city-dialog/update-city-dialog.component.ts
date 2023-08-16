import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CityList } from '../city.component';
import { CountryList } from 'src/app/country/country.component';
import { CountryDataService } from 'src/app/services/country-service/country-data.service';


@Component({
  selector: 'app-update-city-dialog',
  templateUrl: './update-city-dialog.component.html',
  styleUrls: ['./update-city-dialog.component.sass']
})
export class UpdateCityDialogComponent implements OnInit {
  updatedCity: any;
  countries: CountryList[] = [];
  updatedCountry: any;

  constructor(
    public dialogRef: MatDialogRef<UpdateCityDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public city: any,
    private countryDataService: CountryDataService 
  ) {
    // this.updatedCity = { ...city };
    this.listCountries(); 
    this.updatedCity = { ...city, countryId: city.country.id };
    this.updatedCountry = city.country.id;
    
  }

  ngOnInit(): void {
    
    this.updatedCountry = this.city.country.id;
  }

  close() {
    this.dialogRef.close();
  }

  onFormSubmit(form: NgForm) {
    if (form.invalid) return;
    this.updatedCity.name = form.value.name;
    this.updatedCountry = form.value.selectedCountryId;
    console.log('updated city', this.updatedCity);
    this.dialogRef.close(this.updatedCity);
    console.log('country id', this.updatedCity.countryId);
  }

  listCountries() {
    this.countryDataService.listAllCountries().subscribe(
      (response: any[]) => {
        console.log(response);
        this.countries = response;
        console.log('countries', this.countries);
      },
      (error: any) => {
        console.error('Error fetching countries:', error);
      }
    );
  }
}
