import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-slider',
  templateUrl: './profile-slider.component.html',
  styleUrls: ['./profile-slider.component.sass']
})
export class ProfileSliderComponent implements OnInit {
  users: any[] = [
    {
      firstName: 'John',
      lastName: 'Doe',
      profilePicUrl: 'path/to/john-pic.jpg',
      bio: 'Frontend Developer',
    },
    {
      firstName: 'Jane',
      lastName: 'Smith',
      profilePicUrl: 'path/to/jane-pic.jpg',
      bio: 'Photographer',
    },
    {
      firstName: 'John',
      lastName: 'Doe',
      profilePicUrl: 'path/to/john-pic.jpg',
      bio: 'Frontend Developer',
    },
    {
      firstName: 'Jane',
      lastName: 'Smith',
      profilePicUrl: 'path/to/jane-pic.jpg',
      bio: 'Photographer',
    },
    {
      firstName: 'John',
      lastName: 'Doe',
      profilePicUrl: 'path/to/john-pic.jpg',
      bio: 'Frontend Developer',
    },
    {
      firstName: 'Jane',
      lastName: 'Smith',
      profilePicUrl: 'path/to/jane-pic.jpg',
      bio: 'Photographer',
    },
        {
      firstName: 'John',
      lastName: 'Doe',
      profilePicUrl: 'path/to/john-pic.jpg',
      bio: 'Frontend Developer',
    },
    {
      firstName: 'Jane',
      lastName: 'Smith',
      profilePicUrl: 'path/to/jane-pic.jpg',
      bio: 'Photographer',
    },
    // Add more user profiles
  ];

  constructor() {}

  ngOnInit(): void {}

  followUser(user: any) {
    // Implement your follow logic here
    console.log(`Following ${user.firstName} ${user.lastName}`);
  }

  

}