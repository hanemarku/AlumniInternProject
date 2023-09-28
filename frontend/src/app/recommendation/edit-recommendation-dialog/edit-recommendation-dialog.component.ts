import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';
import { RecommendationList } from '../recommendation.component';

@Component({
  selector: 'app-edit-recommendation-dialog',
  templateUrl: './edit-recommendation-dialog.component.html',
  styleUrls: ['./edit-recommendation-dialog.component.sass']
})
export class EditRecommendationDialogComponent implements OnInit {
  updatedRecommendation: any;

  constructor(
    public dialogRef: MatDialogRef<EditRecommendationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public recommendation: RecommendationList
  ) {
    this.updatedRecommendation = { ...recommendation };
  }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  onFormSubmit(form: NgForm) {
    if (form.invalid) return;
    this.updatedRecommendation.comment = form.value.comment;
    this.dialogRef.close(this.updatedRecommendation);
  }

}
