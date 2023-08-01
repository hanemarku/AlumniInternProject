import { Component, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/services/user-service/user-data.service';
import Swal from 'sweetalert2';


export class UserList{
  constructor(
   public id : string,
   public firstname: string,
   public lastname: string,
   public email: string,
   public enabled: boolean,
   public birthday: Date,
   public profilePicUrl: string,
   public phoneNumber: string,
   public city: string,
   public country: string,
   public bio: string,
   public skills: { name: string }[], 
   public interests: {  name: string }[], 
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

  constructor(
    private userService: UserDataService
  ){}

  ngOnInit(): void {
    this.userService.listAllUsers().subscribe(
      response => {
        console.log(response);
        this.users = response;
        console.log(this.users);
      }
    )
    
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
