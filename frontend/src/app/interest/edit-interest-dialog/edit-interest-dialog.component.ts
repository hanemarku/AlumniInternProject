import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InterestComponent } from '../interest.component';



@Component({
  selector: 'app-edit-interest-dialog',
  templateUrl: './edit-interest-dialog.component.html',
  styleUrls: ['./edit-interest-dialog.component.sass']
})
export class EditInterestDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<EditInterestDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public interest: InterestComponent) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close()
  }

  onFormSubmit(form: NgForm) {
    if (form.invalid) return
    
    const updatedInterest = {
      ...this.interest,
      ...form.value
    }
    
    this.dialogRef.close(updatedInterest)
  }


}