import { Component, OnInit, ViewChild, Output, EventEmitter} from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { CountryDataService } from 'src/app/services/country-service/country-data.service';
import { CountryList } from 'src/app/country/country.component';
import { get } from 'jquery';
import { CityList } from 'src/app/city/city.component';
import { CityDataService } from 'src/app/services/city-service/city-data.service';
import { CountryCitySelectorComponent } from 'src/app/country-city-selector/country-city-selector.component';
import { Education } from 'src/app/education/education.component';


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
  selectedImage: string | null = null;

  @ViewChild(CountryCitySelectorComponent) countryCitySelector!: CountryCitySelectorComponent;

  @Output() selectedSkillsChange: EventEmitter<string[]> = new EventEmitter<string[]>();
  @Output() selectedInterestsChange: EventEmitter<string[]> = new EventEmitter<string[]>();
  @Output() selectedEducationChange: EventEmitter<any> = new EventEmitter<any>();


  countries: CountryList[] = [];
  selectedCountry: string | null = null;
  selectedCityFromList = true;
  selectedCity: string | null = null;
  cities: { [key: string]: CityList[] } = {};
  customCity: string | null = null;

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
      title: 'Social Profiles',
      subtitle: 'Your presence on the social network',
      
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
    private cityDataService: CityDataService) {}

  ngOnInit() {

    this.countryDataService.listAllCountries().subscribe(
      (response: CountryList[]) => (this.countries = response) 
    );
 
    this.msform = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirm_pass: ['', [Validators.required]],
      birthday: [null, Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      skills: [[]],
      interests: [[]],
      educations: [[]],
      profilePicUrl: [''],
      bio: [''],
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
    console.log('Selected Education:', this.selectedEducation);
    this.msform.patchValue({
      educations: this.selectedEducation, 
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
    console.log('Form Value:', this.msform.value);
  }

  onFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      const file = inputElement.files[0];
      const reader = new FileReader();

      reader.onload = (e) => {
        this.selectedImage = e.target?.result as string;
        this.msform.patchValue({
          profilePicUrl: this.selectedImage,
        });
      };

      reader.readAsDataURL(file);
    }
  }


  onSelectedCountryChange(selectedCountry: string | null) {
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
