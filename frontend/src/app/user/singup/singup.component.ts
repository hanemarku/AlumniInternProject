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

  selectedSkills: string[] = [];
  selectedInterests: string[] = [];
  selectedEducation: Education[] = [];
  selectedEmployment: Employment[] = [];
  selectedImage: string | null = null;
  profilePicFile: File | undefined;
  emailAvailable: boolean = true;
  educations: Education[] = [];
  employments: Employment[] = [];


  @ViewChild(CountryCitySelectorComponent) countryCitySelector!: CountryCitySelectorComponent;

  @Output() selectedSkillsChange: EventEmitter<string[]> = new EventEmitter<string[]>();
  @Output() selectedInterestsChange: EventEmitter<string[]> = new EventEmitter<string[]>();
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
    private userDataService: UserDataService) {}

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
      password: [''],
      confirm_pass: [''],
      bio: [''],
      skills: [[]],    
      interests: [[]],
      educationHistories: [[]],
      employmentHistories: [[]],
    });
  }

  onSelectedSkillsChange(selectedSkills: string[]) {
    this.selectedSkills = selectedSkills;
    console.log('Selected Skills:', this.selectedSkills);
    
    // this.msform.get('skills')?.setValue(selectedSkills);
    this.msform.patchValue({
      skills: this.selectedSkills,
    });
  }

  onSelectedInterestsChange(selectedInterests: string[]) {
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
    const firstname = this.msform.get('firstname')?.value;
    const lastname = this.msform.get('lastname')?.value;
  
    if (this.profilePicFile) {
      const fileData = new FormData();
      fileData.append('profilePicUrl', this.profilePicFile);
      fileData.append('firstname', firstname);
      fileData.append('lastname', lastname);

      // console log what is in fileData
      console.log('File Data:', fileData);

  
      this.userDataService.uploadFile(fileData).subscribe(
        (response) => {
          console.log('File uploaded successfully:', response);
          this.submitUserData(response);
        },
        (error) => {
          console.error('Error uploading file:', error);
        }
      );
    } else {
      console.log('No file selected');
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
    };

    //console log educations
    console.log('Educations in signup submit:', userData.educationHistories);


  
    this.userDataService.createUser(userData).subscribe(
      (response) => {
        console.log('User created successfully:', response);
      },
      (error) => {
        console.error('Error creating user:', error);
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
