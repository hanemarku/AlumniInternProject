import { Component, OnInit } from '@angular/core';
import { CityList } from '../city/city.component';
import { CountryDataService } from '../services/country-service/country-data.service';
import { NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { UpdateCountryDialogComponent } from './update-country-dialog/update-country-dialog.component';

export class CountryList {
  constructor(
    public id: string,
    public name: string,
    public code: string,
    public cities: CityList[]
  ) { }
}

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.sass']
})

export class CountryComponent implements OnInit {
  countries: CountryList[] = [];
  showValidationErrors: boolean = false;
  selectedCountry: CountryList = new CountryList('', '', '', []);
  showCountry = false;
  constructor(
    private countryDataService: CountryDataService,
    private dialog: MatDialog
  ) { }


  ngOnInit(): void {
    console.log("CountryComponent initialized");
    this.refreshCountries();
  }

  onFormSubmit(form: NgForm) {
    if (form.valid) {
      const country: CountryList = {
        id: '',
        name: form.value.text,
        code: form.value.code,
        cities: []
      };

      this.countryDataService.saveCountry(country).subscribe(
        response => {
          console.log(response);
          this.refreshCountries();
          this.showValidationErrors = false;
          form.resetForm();
        },
        error => {
          console.error(error);
        }
      );
    } else {
      this.showValidationErrors = true;
    }
  }

  refreshCountries() {
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

  saveCountry(form: NgForm) {
    console.log('save', form.value.text);
    this.countryDataService.saveCountry({ name: form.value.text }).subscribe(
      response => {
        console.log(response);
        this.refreshCountries();
        this.showValidationErrors = false;
        form.resetForm();
      },
    );
  }

  onCountryClick(country: CountryList) {
    console.log('country clicked', country);
    this.selectedCountry = country;
    this.showCountry = true;
  }

  deleteCountry(countryId: string) {
    console.log(`delete country ${countryId}`);
    this.countryDataService.deleteCountry(countryId).subscribe(
      response => {
        console.log(response);
        this.refreshCountries();
      }
    )
  }

  openConfirmationDialog(countryId: string) {
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
        const countryToDelete = this.countries.find(country => country.id === countryId);
        if (countryToDelete) {
          this.deleteCountry(countryToDelete.id);
        }
        Swal.fire(
          'Deleted!',
          'Country has been deleted.',
          'success'
        )
      }
    })
  }

  onUpdateClicked(country: CountryList) {
    console.log(`edit country ${this.selectedCountry.id}`);
    const dialogRef = this.dialog.open(UpdateCountryDialogComponent, {
      width: '700px',
      data: this.selectedCountry
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Updated Country Data:', result);
        this.deleteCountry(this.selectedCountry.id);
        this.updateCountry(this.selectedCountry.id, result);
        this.onCountryClick(result);
      }
    });
  }

  updateCountry(countryId: string, updatedCountry: any) {
    console.log(`update country ${countryId}`);
    this.countryDataService.saveCountry(updatedCountry).subscribe(
      response => {
        console.log(response);
        this.refreshCountries();
      },
      error => {
        console.error('Error updating country:', error);
      }
    );
  }
  
}



