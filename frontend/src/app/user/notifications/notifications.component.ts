import { Component, OnInit } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { forkJoin } from 'rxjs';
import { ConnectionRequestService, UserDto } from 'src/app/services/connection-request/connection-request.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.sass']
})
export class NotificationsComponent  implements OnInit{
  notifications: any[] = [];
  users: any[] = [];
  profilePicUrl: SafeUrl | string = '';
  userId: string = JSON.parse(localStorage.getItem('user') || '{}').id;
  friends: any[] = [];
  showFriends: boolean = false;


  constructor(
    private userService: UserDataService,
    private connectionService: ConnectionRequestService,
    
  ) { }

  ngOnInit(): void {
    this.getUsers();
    this.fetchFriends(this.userId);
  }

  
  toggleFriendsSection(): void {
    this.showFriends = !this.showFriends;
  }

  fetchFriends(userId: string): void {
    this.connectionService.getUserFriends(userId).subscribe(
      (friends: any[]) => {
        this.friends = friends;

        const profilePicRequests = this.friends.map(user =>
          this.userService.getUserProfilePic(user.email)
        );
   
        forkJoin(profilePicRequests).subscribe(
          (profilePicUrls: SafeUrl[]) => {
            this.friends.forEach((friend, index) => {
              friend.profilePicUrl = profilePicUrls[index];
              
            });
          },
      (error) => {
        console.error('Error fetching friends:', error);
      }
      );
    });
  }


  getUsers() {
    this.connectionService.getReceivedConnectionRequests(this.userId).subscribe(users => {
      this.users = users.connectionsReceived;
      // console.log("status on init" + this.users.requester.status)

      const profilePicRequests = this.users.map(user =>
        this.userService.getUserProfilePic(user.requester.email)
      );
 
      forkJoin(profilePicRequests).subscribe(
        (profilePicUrls: SafeUrl[]) => {
          this.users.forEach((user, index) => {
            user.profilePicUrl = profilePicUrls[index];
            
          });
        },
        (error) => {
          console.error('Error fetching profile pictures:', error);
        }
      );
    });
  }

  acceptRequest(user: any) {
    console.log("accept" + user.id)
    console.log("name " + user.email)
    this.connectionService.acceptConnectionRequest(user.id, this.userId).subscribe(
      () => {
        window.location.reload();
        user.followStatus = 'accepted';
      },
      (error) => {
        console.error('Error accepting connection request:', error);
      }
    );
  }

  declineRequest(user: any) {
    this.connectionService.rejectConnectionRequest(user.id, this.userId).subscribe(
      () => {
        window.location.reload();
        user.followStatus = 'declined';
      },
      (error) => {
        console.error('Error accepting connection request:', error);
      }
    );
  }

  getAnimationDelay(index: number): string {
    const delayInSeconds = index * 0.2; 
    return `${delayInSeconds}s`;
  }
  
}
