import { Component, OnInit, ViewChild, Output, EventEmitter} from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { CountryDataService } from 'src/app/services/country-service/country-data.service';
import { CountryList } from 'src/app/country/country.component';
import { get } from 'jquery';
import { CityList } from 'src/app/city/city.component';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { CountryCitySelectorComponent } from 'src/app/country-city-selector/country-city-selector.component';
import { User, UserDataService, UserTest } from 'src/app/services/user-service/user-data.service';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { ValidationErrors } from '@angular/forms';
import { tap } from 'rxjs/operators';
import { Education } from 'src/app/services/education-service/education-data.service';
import { Employment } from 'src/app/services/employment-service/employment-data.service';
import { Skill } from 'src/app/skill-search/skill-search.component';
import { Interest } from 'src/app/interest-search/interest-search.component';
import { Router } from '@angular/router';
import { MessageService } from 'src/app/services/messages/message.service';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { NotificationType } from 'src/app/enum/header-type.enum';


@Component({
  selector: 'app-singup',
  templateUrl: './singup.component.html',
  styleUrls: ['./singup.component.sass'],
  animations: [
    trigger('fieldsetAnimation', [
      transition(':increment', [
        style({ opacity: 0, transform: 'scale(0.8)' }),
        animate('800ms ease-in-out', style({ opacity: 1, transform: 'scale(1)' }))
      ]),
     
    ])
  ]
})
export class SingupComponent implements OnInit {

  selectedSkills: Skill[] = [];
  selectedInterests: Interest[] = [];
  selectedEducation: Education[] = [];
  selectedEmployment: Employment[] = [];
  selectedImage: string | null = null;
  profilePicFile: File | undefined;
  emailAvailable: boolean = true;
  educations: Education[] = [];
  employments: Employment[] = [];


  @ViewChild(CountryCitySelectorComponent) countryCitySelector!: CountryCitySelectorComponent;

  @Output() selectedSkillsChange: EventEmitter<Skill[]> = new EventEmitter<Skill[]>();
  @Output() selectedInterestsChange: EventEmitter<Interest[]> = new EventEmitter<Interest[]>();
  @Output() selectedEducationChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() selectedEmploymentChange: EventEmitter<any> = new EventEmitter<any>();


  countries: CountryList[] = [];
  selectedCountry: CountryList | null = null;
  selectedCityFromList = true;
  selectedCity: string | null = null;
  cities: { [key: string]: CityList[] } = {};

  msform: FormGroup<any> = new FormGroup({});
  currentStep = 0;
  fieldsets = [
    {
      title: 'Personal Details',
      subtitle: 'Tell us something more about you',
      inputs: [
        { name: 'firstname', placeholder: 'First Name' },
        { name: 'lastname', placeholder: 'Last Name' },
        { name: 'birthday', placeholder: 'Birthday', type: 'date' },
        { name: 'phoneNumber', placeholder: 'Phone Number' },
      

      ],
    },
    {
      title: 'Social Profiles and Education',
      subtitle: 'Your presence on the social network and your education',
      
    },
    {
      title: 'Work Exprience',
      subtitle: 'Tell us more about your work exprience',
      
    },
    {
      title: 'Digital Skills and Interests',
      subtitle: 'Tell us more about your skills and interests',
      
    },
    {
      title: 'Create your account',
      subtitle: 'Fill in your credentials',
      inputs: [
        { name: 'email', placeholder: 'Email' },
        { name: 'password', placeholder: 'Password', type: 'password' },
        { name: 'confirm_pass', placeholder: 'Confirm Password', type: 'password' },
      ],
    },
  ];

  constructor(private formBuilder: FormBuilder,
    private countryDataService: CountryDataService,
    private cityDataService: CityDataService,
    private userDataService: UserDataService,
    private router: Router,
    public messageService: MessageService,
    private notificationSerivce: NotificationService) {}

  ngOnInit() {

    this.countryDataService.listAllCountries().subscribe(
      (response: CountryList[]) => (this.countries = response) 
    );
    
 
    this.msform = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email], this.checkEmailAvailability.bind(this)],
      birthday: [null],
      phoneNumber: [''],
      city: [''],
      country: [''],
      password: ['', Validators.required],
      confirm_pass: ['', Validators.required],
      bio: [''],
      skills: [[]],    
      interests: [[]],
      educationHistories: [[]],
      employmentHistories: [[]],
    },
    {
      asyncValidators: [this.passwordMatchValidatorAsync],
    }
    );
  }

  onSelectedSkillsChange(selectedSkills: Skill[]) {
    this.selectedSkills = selectedSkills;
    console.log('Selected Skills:', this.selectedSkills);
    
    // this.msform.get('skills')?.setValue(selectedSkills);
    this.msform.patchValue({
      skills: this.selectedSkills,
    });
  }

  onSelectedInterestsChange(selectedInterests: Interest[]) {
    this.selectedInterests = selectedInterests;
    console.log('Selected Interests:', this.selectedInterests);
    this.msform.patchValue({
      interests: this.selectedInterests,
    });
  }

  onSelectedEducationChange(selectedEducation: Education[]) {
    this.selectedEducation = selectedEducation;
    console.log('Selected Education in signup :', this.selectedEducation);
    this.msform.patchValue({
      educationHistories: this.selectedEducation,
    });
  }

  onSelectedEmploymentChange(selectedEmployment: Employment[]) {
    this.selectedEmployment = selectedEmployment;
    console.log('Selected Employment in signup :', this.selectedEmployment);
    this.msform.patchValue({
      employmentHistories: this.selectedEmployment,
    });
  }

  

  
  nextStep() {
    if (this.currentStep < this.fieldsets.length - 1) {
      this.currentStep++;
      console.log(this.currentStep);
    }
  }


  previousStep() {
    if (this.currentStep > 0) {
      this.currentStep--;
      console.log(this.currentStep);
    }
  }



  submitForm() {
    // const firstname = this.msform.get('firstname')?.value;
    // const lastname = this.msform.get('lastname')?.value;
    const email = this.msform.get('email')?.value;
  
    if (this.profilePicFile) {
      const fileData = new FormData();
      fileData.append('profilePicUrl', this.profilePicFile);
      fileData.append('email', email);
      // fileData.append('lastname', lastname);

      console.log('File Data:', fileData);

  
      this.userDataService.uploadFile(fileData).subscribe(
        (response) => {
          console.log('File uploaded successfully:', response);
          this.submitUserData(response);
        },
        (error) => {
          this.notificationSerivce.notify(NotificationType.ERROR, "Something went wrong. Unable to upload file");
          console.error('Error uploading file:', error);
        }
      );
    } else {
      console.log('No file selected');
      this.notificationSerivce.notify(NotificationType.ERROR, "Please select a profile picture");
    }
  }
  
  
  submitUserData(fileUrl: string) {
    console.log('File URL:', fileUrl);
    const userData = {
      firstname: this.msform.get('firstname')?.value,
      lastname: this.msform.get('lastname')?.value,
      email: this.msform.get('email')?.value,
      password: this.msform.get('password')?.value,
      birthday: this.msform.get('birthday')?.value,
      city: this.msform.get('city')?.value,
      country: this.msform.get('country')?.value,
      skills: this.msform.get('skills')?.value,
      interests: this.msform.get('interests')?.value,
      educationHistories: this.msform.get('educationHistories')?.value,
      profilePicUrl: fileUrl,
      phoneNumber: this.msform.get('phoneNumber')?.value,
      bio: this.msform.get('bio')?.value,
      employmentHistories: this.msform.get('employmentHistories')?.value,
    };

    console.log('skills in signup submit:', userData.skills);
    console.log('interests in signup submit:', userData.interests);
    console.log('Educations in signup submit:', userData.educationHistories);
    console.log('Employments in signup submit:', userData.employmentHistories);


  
    this.userDataService.createUser(userData).subscribe(
      (response) => {
        console.log('User created successfully:', response);
        this.notificationSerivce.notify(NotificationType.SUCCESS ,'Signup successful!');
        this.router.navigate(['/signin']);
        
        this.messageService.showSuccessMessage('Signup successful! Now you can login.');
        // this.router.navigate(['/success-page']);
      },
      (error) => {
        console.error('Error creating user:', error);
        this.notificationSerivce.notify(NotificationType.ERROR ,'Signup failed!');
        this.messageService.showErrorMessage('Signup failed. Please try again. Open console for more details.');
      }
    );
  }
  
  
  

  onFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const files = inputElement?.files;
  
    if (files && files.length > 0) {
      this.profilePicFile = files[0];
  
      const reader = new FileReader();
      reader.onload = (e) => {
        this.selectedImage = e.target?.result as string;
      };
      reader.readAsDataURL(this.profilePicFile);
    }
  }
  
  
  checkEmailAvailability(control: AbstractControl): Observable<ValidationErrors | null> {
    const email = control.value;
    return this.userDataService.checkEmailAvailability(email).pipe(
      tap((response: any) => {
        this.emailAvailable = response.available;
      }),
      map((response: any) => {
        if (response.available) {
          return null; 
        } else {
          return { emailExists: true }; 
        }
      }),
      catchError(() => {
        return of(null); 
      })
    );
  }

  passwordMatchValidatorAsync = (control: AbstractControl): Promise<ValidationErrors | null> => {
    const password = control.get('password')?.value;
    const confirmPass = control.get('confirm_pass')?.value;
  
    return new Promise((resolve) => {
      setTimeout(() => {
        if (password !== confirmPass) {
          resolve({ passwordsNotMatch: true });
          console.log('passwords not match', password, confirmPass, password !== confirmPass);
        } else {
          resolve(null);
        }
      }, 500);
    });
  };
  

  


  onSelectedCountryChange(selectedCountry: CountryList | null) {
    this.selectedCountry = selectedCountry;
    console.log('Selected Country testtt:', this.selectedCountry);
    this.msform.get('country')?.setValue(selectedCountry);
  }

  onSelectedCityChange(selectedCity: string | null) {
    this.selectedCity = selectedCity;
    console.log('Selected City testtt:', this.selectedCity);
    this.msform.get('city')?.setValue(selectedCity);
  }
}
