import { Component, OnInit } from '@angular/core';
import { RecommendationDataService } from '../services/recommendation-service/recommendation-data.service';
import { NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { EditRecommendationDialogComponent } from './edit-recommendation-dialog/edit-recommendation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ListUsersComponent, UserList } from '../user/list-users/list-users.component';
import { UserDataService } from '../services/user-service/user-data.service';
import { SafeUrl } from '@angular/platform-browser';
import { AuthenticationService } from '../services/authenication-service/authentication.service';
import { User } from '../services/authenication-service/authentication.service';

export class RecommendationList {
  constructor(
    public id: string,
    public comment: string,
    public timestamp: Date,
    public recommender: string,
    public recommendedUser: string,
    public profilePicUrl: SafeUrl,
    public User: UserList | undefined | null,
    public RecommendedUser: UserList | undefined | null

  ) { }
}

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.sass']
})
export class RecommendationComponent implements OnInit {

  receivedRecommendations: RecommendationList[] = [];
  recommendations: RecommendationList[] = [];
  givenRecommendations: RecommendationList[] = [];
  recommendedUserId: string = '';
  recommenderId: string = '';
  showValidationErrors: boolean = false;
  showReceived: boolean = true;
  showGiven: boolean = false;
  userName: string = '';
  user: UserList | undefined | null = null;
  loggedInUser: User | null = null;
  loggedInUserEmail: string | null = null;


  constructor(
    private recommendationDataService: RecommendationDataService,
    private userService: UserDataService,
    private authService: AuthenticationService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.refreshRecommendations();
    this.loadLoggedInUser();

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
    this.recommendationDataService.listAllRecommendations().subscribe(
      (response: any[]) => {
        console.log(response);
        this.receivedRecommendations = response;
        this.fetchUserInformation(this.receivedRecommendations);
      },
      (error: any) => {
        console.error('Error fetching recommendations:', error);
      }
    );
  }

  fetchUserInformation(recommendations: RecommendationList[]) {
    recommendations.forEach(recommendation => {
      this.fetchRecommenderInformation(recommendation);
      this.fetchRecommendedUserInformation(recommendation);
    });
  }

  fetchRecommenderInformation(recommendation: RecommendationList) {
    this.userService.getUserById(recommendation.recommender).subscribe(
      (user: UserList) => {
        recommendation.User = user;
      },
      (error: any) => {
        console.error(`Error fetching recommender information for user ID ${recommendation.recommender}:`, error);
      }
    );
  }

  fetchRecommendedUserInformation(recommendation: RecommendationList) {
    this.userService.getUserById(recommendation.recommendedUser).subscribe(
      (user: UserList) => {
        recommendation.RecommendedUser = user;
      },
      (error: any) => {
        console.error(`Error fetching recommendedUser information for user ID ${recommendation.recommendedUser}:`, error);
      }
    );
  }


  loadLoggedInUser() {
    this.loggedInUserEmail = this.authService.getUserFromLocalStorage()?.email || null;
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
    if (this.loggedInUserEmail === recommendation.User?.email) {
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
  }

  // onLeaveRecommendationClicked() {
  //   const dialogRef = this.dialog.open(SaveRecommendationDialogComponent, {
  //     width: '700px',
  //     data: null, // You can pass any initial data needed for creating a new recommendation
  //   });
  
  //   dialogRef.afterClosed().subscribe((result: any) => {
  //     if (result) {
  //       // Handle the result (new recommendation data) here
  //       console.log('New Recommendation Data:', result);
  //       // Call the service to save the new recommendation
  //       this.recommendationDataService.saveRecommendation(result).subscribe(
  //         (response) => {
  //           console.log('Recommendation Saved:', response);
  //           this.refreshRecommendations();
  //         },
  //         (error) => {
  //           console.error('Error saving recommendation:', error);
  //         }
  //       );
  //     }
  //   });
  // }







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
    this.recommendationDataService.listReceived(recommendedUserId).subscribe(
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