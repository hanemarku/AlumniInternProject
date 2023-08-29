import { Component, OnInit } from '@angular/core';
import { RecommendationDataService } from '../services/recommendation-service/recommendation-data.service';
import { NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { EditRecommendationDialogComponent } from './edit-recommendation-dialog/edit-recommendation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ListUsersComponent, UserList } from '../user/list-users/list-users.component';
import { UserDataService } from '../services/user-service/user-data.service';
import { SafeUrl } from '@angular/platform-browser';

export class RecommendationList {
  constructor(
    public id: string,
    public comment: string,
    public timestamp: Date,
    public recommender: string,
    public recommendedUser: string,
    public profilePicUrl: SafeUrl

  ){}
}

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.sass']
})
export class RecommendationComponent implements OnInit {

  receivedRecommendations: RecommendationList[] = [];
  givenRecommendations: RecommendationList[] = [];
  recommendedUserId: string = '';
  recommenderId: string = '';
  showValidationErrors: boolean = false;
  showReceived: boolean = true;
  showGiven: boolean = false;


  constructor(
    private recommendationDataService: RecommendationDataService,
    private userService: UserDataService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.refreshRecommendations();

  }


  // refreshRecommendations() {
  //   this.recommendationDataService.listAllRecommendations().subscribe(
  //     (response: any[]) => {
  //       console.log(response);
  //       this.recommendations = response;
  //     },
  //     (error: any) => {
  //       console.error('Error fetching recommendations:', error);
  //     }
  //   );
  // }

  refreshRecommendations() {
    console.log('refresh recommendations');
    if (this.showReceived) {
        this.listReceivedRecommendations(this.recommenderId); // Corrected parameter name
        console.log('received recommendations', this.receivedRecommendations);
    } else {
        this.listGivenRecommendations(this.recommendedUserId);
        console.log('given recommendations', this.givenRecommendations);
    }
}



  deleteRecommendation(recommendationId: string) {
    console.log(`delete recommendation ${recommendationId}`);
    this.recommendationDataService.deleteRecommendation(recommendationId).subscribe(
      response => {
        console.log(response);
        this.refreshRecommendations();
      }
    )
  }


  updateRecommendation(recommendationId: string, updatedRecommendation: any) {
    console.log(`update recommendation ${recommendationId}`);
    this.recommendationDataService.updateRecommendation(recommendationId, updatedRecommendation).subscribe(
      response => {
        console.log(response);
        this.refreshRecommendations();
      },
      error => {
        console.error('Error updating recommendation:', error);
      }
    );
  }

  saveRecommendation(form: NgForm) {
    console.log('save', form.value.text);
    this.recommendationDataService.saveRecommendation({ comment: form.value.text }).subscribe(
      response => {
        console.log(response);
        this.refreshRecommendations();
        this.showValidationErrors = false;
        form.resetForm();
      },
    );
  }

  onEditClicked(recommendation: RecommendationList) {
    console.log(`edit recommendation ${recommendation.id}`);
    const dialogRef = this.dialog.open(EditRecommendationDialogComponent, {
      width: '700px',
      data: recommendation
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Updated Recommendation Data:', result);
        this.recommendationDataService.updateRecommendation(recommendation.id, result).subscribe(
          response => {
            console.log('Recommendation Updated:', response);
            this.refreshRecommendations();
          },
          error => {
            console.error('Error updating recommendation:', error);
          }
        );
      }
    });
  }


  openConfirmationDialog(recommendationId: string) {
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
        const recommendationToDelete = this.showReceived
          ? this.receivedRecommendations.find(recommendation => recommendation.id === recommendationId)
          : this.givenRecommendations.find(recommendation => recommendation.id === recommendationId);
  
        if (recommendationToDelete) {
          this.deleteRecommendation(recommendationToDelete.id);
        }
        Swal.fire(
          'Deleted!',
          'Recommendation has been deleted.',
          'success'
        )
      }
    });
  }
  

  listGivenRecommendations(recommendedUserId: string) {
    console.log(`list given recommendations for ${recommendedUserId}`);
    this.recommendationDataService.listGivenRecommendations(recommendedUserId).subscribe(
        (response: any[]) => {
            console.log(response);
            this.givenRecommendations = response;
            this.recommendedUserId = response[0].recommendedUser.toString(); // Convert UUID to string
        },
        (error: any) => {
            console.error('Error fetching given recommendations:', error);
        }
    );
}

listReceivedRecommendations(recommendedUserId: string) {
    this.recommendationDataService.listReceivedRecommendations(recommendedUserId).subscribe(
        (response: RecommendationList[]) => {
            console.log(response);
            this.receivedRecommendations = response;
        },
        (error: any) => {
            console.error('Error fetching received recommendations:', error);
        }
    );
}

  showReceivedRecommendations() {
    this.showReceived = true;
    this.showGiven = false;
    this.refreshRecommendations();
  }
  
  showGivenRecommendations() {
    this.showReceived = false;
    this.showGiven = true;
    this.refreshRecommendations();
  }


}
