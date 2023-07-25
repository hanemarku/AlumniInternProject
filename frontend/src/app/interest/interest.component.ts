import { Component, Input, OnInit } from '@angular/core';
import { InterestDataService } from '../services/interest-service/interest-data.service';
import { NgForm } from '@angular/forms';
import { EditInterestDialogComponent } from './edit-interest-dialog/edit-interest-dialog.component';
import { MatDialog } from '@angular/material/dialog';


export class InterestComponent {
  
  constructor(
    public interestId: string,
    public name: string
  ) { }
}

@Component({
  selector: 'app-interest',
  templateUrl: './interest.component.html',
  styleUrls: ['./interest.component.sass']
})

export class InterestListComponent implements OnInit {

  interests: InterestComponent[] = [];
  showValidationErrors: boolean = false;

  constructor(
    private interestDataService: InterestDataService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.refreshInterests();
  }

  onFormSubmit(form: NgForm) {
    console.log("FORM SUBMITTED");
    console.log(form);
    if (form.invalid) {
      this.showValidationErrors = true;
    } else {
      this.saveInterest(form);
    }
  }  
  
  onEditClicked(interest: InterestComponent) {
    console.log(`edit interest ${interest.interestId}`);
    const dialogRef = this.dialog.open(EditInterestDialogComponent, {
      width: '400px',
      data: interest
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result && result.interestId) {
        this.updateInterest(result.interestId, result);
      }
    });
  }
  
  onDeleteClicked(interest: InterestComponent) {
    console.log(`delete interest ${interest.interestId}`);
    if (interest.interestId) {
      this.deleteInterest(interest.interestId);
    }
  }

  refreshInterests() {
    this.interestDataService.listAllInterests().subscribe(
      (response: any[]) => {
        console.log(response);
        this.interests = response;
      },
      (error: any) => {
        console.error('Error fetching interests:', error);
      }
    );
  }

  deleteInterest(interestId: string) {
    console.log(`delete interest ${interestId}`);
    this.interestDataService.deleteInterest(interestId).subscribe(
      response => {
        console.log(response);
        this.refreshInterests();
      }
    )
  }


  updateInterest(interestId: string, updatedInterest: any) {
    console.log(`update interest ${interestId}`);
    this.interestDataService.updateInterest(interestId, updatedInterest).subscribe(
      response => {
        console.log(response);
        this.refreshInterests();
      }
    )
  }

saveInterest(form: NgForm) {
  console.log('save', form.value.text);
  this.interestDataService.saveInterest({ name: form.value.text }).subscribe(
    response => {
      console.log(response);
      this.refreshInterests();
      this.showValidationErrors = false;
      form.resetForm();
    },
  );
}


}
