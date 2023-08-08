import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CountryComponent } from '../country.component';

@Component({
  selector: 'app-update-country-dialog',
  templateUrl: './update-country-dialog.component.html',
  styleUrls: ['./update-country-dialog.component.sass']
})
export class UpdateCountryDialogComponent implements OnInit {
  updatedCountry: any;

  constructor(
    public dialogRef: MatDialogRef<UpdateCountryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public country: any
  ) {
    this.updatedCountry = { ...country };
  }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  onFormSubmit(form: NgForm) {
    if (form.invalid) return;
    this.updatedCountry.name = form.value.name;
    this.dialogRef.close(this.updatedCountry);
  }
}
