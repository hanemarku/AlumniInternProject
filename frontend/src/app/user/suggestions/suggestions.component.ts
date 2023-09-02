import { Component, OnInit } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { forkJoin } from 'rxjs';
import { ConnectUserDTO, ConnectionRequestService, UserDto } from 'src/app/services/connection-request/connection-request.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.sass']
})
export class SuggestionsComponent implements OnInit {
  suggestions!: any[];
  profilePicUrl: SafeUrl | string = '';
  userId: string = '';


  constructor(
    private userService: UserDataService,
    private connectionService: ConnectionRequestService,
  ) {}

  ngOnInit(): void {
    const userId = JSON.parse(localStorage.getItem('user') || '{}').id;

    this.connectionService.getProfileSuggestions(userId).subscribe(suggestions => {
      console.log(suggestions);
      this.suggestions = suggestions;
      console.log(this.suggestions);

      this.connectionService.getSentConnectionRequests(userId).subscribe(
        (response) => {
          console.log('Sent connection requests test:', response);

          if (Array.isArray(response.connectionsSent)) {
            const sentConnectionRequests = response.connectionsSent;
            
            sentConnectionRequests.forEach((userSuggestion: any) => {
              console.log("user suggestion: " , userSuggestion);
              const suggestedUser = this.suggestions.find(suggestion => suggestion.user.id === userSuggestion.requestee.id);

              if (suggestedUser) {
                if (userSuggestion.status === 'PENDING') {
                  suggestedUser.user.followStatus = 'pending';
                } else if (userSuggestion.status === 'ACCEPTED') {
                  suggestedUser.user.followStatus = 'accepted';
                } else if (userSuggestion.status === 'REJECTED') {
                  suggestedUser.user.followStatus = 'rejected';
                }
              }
              
            });
          } else {
            console.error('Invalid response format for sent connection requests.');
          }
        },
        (error) => {
          console.error('Error fetching sent connection requests:', error);
        }
      );
      

      const profilePicRequests = this.suggestions.map(suggestion =>
        this.userService.getUserProfilePic(suggestion.user.email)
      );

 
      forkJoin(profilePicRequests).subscribe(
        (profilePicUrls: SafeUrl[]) => {
          this.suggestions.forEach((userSuggestion, index) => {
            userSuggestion.user.profilePicUrl = profilePicUrls[index];
            console.log(userSuggestion.user.profilePicUrl);
            console.log("oninit status " + userSuggestion.user.followStatus);
          });
        },
        (error) => {
          console.error('Error fetching profile pictures:', error);
        }
      );
    });
  }
  

  sendFollowRequest(receiverId: string): void {
    this.userId = JSON.parse(localStorage.getItem('user') || '{}').id;
    const senderId = this.userId;
    console.log("user id " + senderId);
    console.log("receiver id" + receiverId);
    this.connectionService.sendFollowRequest(senderId, receiverId).subscribe(
      (response) => {
        console.log('status:', response.status);

        const user = this.suggestions.find(u => u.id === receiverId);
        console.log('user:', user);

        if (user) {
          if (response.status === 'PENDING') {
            console.log('pending');
            user.followStatus = 'PENDING';
          } else if(response.status === 'ACCEPTED'){
            user.followStatus = 'ACCEPTED';
          }else if(response.status === 'REJECTED'){
            user.followStatus = 'REJECTED';
          }
        }
        window.location.reload();
        console.log('user status :', user?.followStatus);
        console.log('Follow request sent:', response);
      },
      (error) => {
        console.error('Error sending follow request:', error);
      }
    );
  }
  
}
