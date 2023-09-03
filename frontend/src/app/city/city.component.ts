import { Component, OnInit } from '@angular/core';
import { CityDataService } from '../services/city-service/city-data.service';
import { FormControl, NgForm, Validators, FormBuilder, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { UpdateCityDialogComponent } from './update-city-dialog/update-city-dialog.component';
import { CountryDataService } from '../services/country-service/country-data.service';
import { CountryList } from '../country/country.component';

export class CityList {
  constructor(
    public id: string,
    public name: string,
    public country: CountryList | undefined | null,

  ) { }

}

export class City{
  constructor(
    public name: string,
    public countryId: CountryList | undefined | null,
  ){}
}

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.sass']
})
export class CityComponent implements OnInit {

  cities: CityList[] = [];
  showValidationErrors: boolean = false;
  selectedCity: CityList = new CityList( '', '', null);
  showCity = false;
  showCountry = false;
  countries: CountryList[] = [];
  selectedCountryId: string = '';
  selectedCountryName: string = '';
  selectedCountry: CountryList | undefined |  null = null;


  constructor(
    private cityDataService: CityDataService,
    private countryDataService: CountryDataService,
    private dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    console.log("CityComponent initialized");
    this.refreshCities();
    this.listCountries();

    this.selectedCountryId = '';

  }



  onFormSubmit(form: NgForm) {
    if (form.valid) {
      var countryId = form.value.selectedCountryId;
      console.log('countryId', countryId);
      this.selectedCountry = this.countries.find(country => country.id === countryId);
      console.log('selectedCountry', this.selectedCountry);

      const city: City = {
        name: form.value.cityName,
        countryId: countryId
      };

      console.log('city test omimmot', city.name, city.countryId);
  
      this.cityDataService.saveCity(city).subscribe(
        response => {
          console.log('Save response:', response);
          this.refreshCities();
          this.showValidationErrors = false;
          form.resetForm();
        },
        error => {
          console.error('Save error:', error);
        }
      );
    } else {
      this.showValidationErrors = true;
    }
  }


  refreshCities() {
    this.cityDataService.listCities().subscribe(
      (response: any[]) => {
        console.log(response);
        this.cities = response;
  
        for (const city of this.cities) {
          this.selectCountryById(city.country?.id);
        }
      },
      (error: any) => {
        console.error('Error fetching cities:', error);
      }
    );
  }



  saveCity(form: NgForm) {
    console.log('save', form.value.text);
    this.cityDataService.saveCity({ name: form.value.text }).subscribe(
      response => {
        console.log(response);
        this.refreshCities();
        this.showValidationErrors = false;
        form.resetForm();
      }
    );
  }

  onCityClick(city: CityList) {
    console.log('city clicked', city);
    this.selectedCity = city;
    this.selectedCountryName = this.selectedCity.country?.name || '';
    console.log('selectedCountry', this.selectedCountry);
    console.log('test', city.country?.id);
    this.showCity = true;
    this.showCountry = true;
  }


  deleteCity(cityId: string) {
    console.log(`delete city ${cityId}`);
    this.cityDataService.deleteCity(cityId).subscribe(
      response => {
        console.log(response);
        this.refreshCities();
      }
    )
  }


  openConfirmationDialog(cityId: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        const cityToDelete = this.cities.find(city => city.id === cityId);
        if (cityToDelete) {
          this.deleteCity(cityToDelete.id);
        }
        Swal.fire(
          'Deleted!',
          'City has been deleted.',
          'success'
        )
      }
    })
  }

  onUpdateClicked(city: CityList) {
    const dialogRef = this.dialog.open(UpdateCityDialogComponent, {
      width: '700px',
      data: city
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Updated City Data:', result);
        this.deleteCity(this.selectedCity.id);
        this.updateCity(city.id, result);
        this.onCityClick(this.selectedCity);
      }
    });
  }

  updateCity(cityId: string, updatedCity: any) {
    console.log(`update city ${cityId}`);

    this.cityDataService.saveCity(updatedCity).subscribe(
      response => {
        console.log(response);
        this.refreshCities();
      },
      error => {
        console.error('Error updating city:', error);
      }
    );
  }

  listCountries() {
    this.countryDataService.listAllCountries().subscribe(
      (response: any[]) => {
        console.log(response);
        this.countries = response;
      },
      (error: any) => {
        console.error('Error fetching countries:', error);
      }
    );
  }

  selectCountryById(countryId: string | undefined | null) {
    console.log('selectCountryById', countryId);
    const selectedCountry = this.countries.find(country => country.id === countryId);
    if (selectedCountry) {
      this.selectedCountry = selectedCountry;
      this.selectedCountryId = selectedCountry.id;
      this.selectedCountryName = selectedCountry.name;
      console.log('selectedCountry', this.selectedCountry);
    }

    
    
  }

  
  


}