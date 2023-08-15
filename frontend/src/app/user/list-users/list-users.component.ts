import { Component, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/services/user-service/user-data.service';
import Swal from 'sweetalert2';
import { SafeUrl } from '@angular/platform-browser';
import { User } from 'src/app/services/authenication-service/authentication.service';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { Education } from 'src/app/services/education-service/education-data.service';
import { Employment } from 'src/app/services/employment-service/employment-data.service';

export class UserList{
  constructor(
   public id : string,
   public firstname: string,
   public lastname: string,
   public email: string,
   public enabled: boolean,
   public birthday: Date,
   public profilePicUrl: SafeUrl | string,
   public phoneNumber: string,
   public city: string,
   public country: string,
   public bio: string,
   public skills: { name: string }[], 
   public interests: {  name: string }[], 
   public educationHistories: Education[],
   public employmentHistories: Employment[],
   public role: string

  ){}
}

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.sass']
})
export class ListUsersComponent implements OnInit{

  users: UserList[] = [];
  dtOptions: DataTables.Settings = {};
  selectedUser: UserList | null = null;
  profilePicUrl: SafeUrl | string = '';
  userEmail: string = ''; 
  profilePicUrls: string[] = [];




  constructor(
    private userService: UserDataService,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private notificationService: NotificationService,
  ){}

  ngOnInit(): void {

      this.userService.listAllUsers().subscribe(
        async (users: UserList[]) => {
          for (const user of users) {

            console.log("user-> " + user.email);
            console.log("user education-> " + user.educationHistories);
            this.userService.getUserProfilePic(user.email).subscribe(
              (url: SafeUrl) => {
                user.profilePicUrl = url;
              },
              (error) => {
                this.notificationService.notify(NotificationType.ERROR,  'Error fetching profile picture pf users');
                console.error('Error fetching profile picture:', error);
              }
            );
          }
          this.users = users;
        }
      );
  
    
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
    };
  }

  getCountryName(country: any): string {
    return country ? country.name : 'Unknown';
  }

  getRoleName(role: any): string {
    return role ? role.name : 'Unknown';
  }

  openUserModal(user: UserList): void {
    this.selectedUser = user;
  }


  
  toggleUserStatus(userId: string, event: any): void {
    const enabled = event.target.checked;
    this.userService.updateEnabledStatus(userId, enabled).subscribe(
      response => {
        const user = this.users.find(u => u.id === userId);
        console.log("user-> " + user?.email);
        if (user) {

          user.enabled = response.enabled;
          console.log("user.enabled-> " + user.enabled);
        }
      },
      error => {
        console.error(error);
      }
    );
  }

  removeUser(userId: string): void {
    this.userService.deleteUser(userId).subscribe(
      () => {
        this.users = this.users.filter(user => user.id !== userId);
        Swal.fire('Success', 'User removed successfully!', 'success');
      },
      error => {
        Swal.fire('Error', 'An error occurred while removing the user.', 'error');
      }
    );
  }

  openConfirmDialog(userId: string): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this user!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, remove it!',
      cancelButtonText: 'Cancel',
    }).then(result => {
      if (result.isConfirmed) {
        this.removeUser(userId);
      }
    });
  }

  

}
