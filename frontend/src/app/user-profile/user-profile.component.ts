import { Component } from '@angular/core';
import { User, UserDataService } from '../services/user-service/user-data.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Skill } from '../skill-search/skill-search.component';
import { AuthenticationService } from '../services/authenication-service/authentication.service';
import { UserList } from '../user/list-users/list-users.component';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.sass']
})
export class UserProfileComponent {
  public user : User = {} as User;
  public user2 : UserList   = {} as UserList;
  public age: String = '';

  
    constructor(
      private userService: UserDataService,
      private authService: AuthenticationService,
    ) { }

    ngOnInit() {

      var email = this.authService.getUserFromLocalStorage().email;

      this.userService.getUserByEmail(email).subscribe(
        async (user: UserList) => {
          this.user2 = user; 
        },
        (error) => {
          console.error('Error fetching user:', error);
        }
      );


      this.user = JSON.parse(localStorage.getItem('user') || '{}');
      var email = this.user.email;
      this.userService.getUserProfilePic(email).subscribe(
        (url: SafeUrl) => {
          this.user.profilePicUrl = url;
        },
        (error) => {
          console.error('Error fetching profile picture:', error);
        }
      );
    }

    findAge(birthday: Date): string {
      var today = new Date();
      var birthDate = new Date(birthday);
      var age = today.getFullYear() - birthDate.getFullYear();
      var m = today.getMonth() - birthDate.getMonth();
      if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate()))
      {
        age--;
      }
      return age.toString();
    }

    getCountryName(country: any): string {
      return country ? country.name : 'Unknown';
    }

    
  getSkillName(skill: any): string {
    if (skill.name) {
      return skill.name;
    } else {
      return 'Unknown Skill';
    }
  }

 
}
