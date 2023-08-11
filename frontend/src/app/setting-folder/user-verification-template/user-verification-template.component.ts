import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Setting, SettingService } from 'src/app/services/setting-service/setting.service';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDataService } from 'src/app/services/user-service/user-data.service';


@Component({
  selector: 'app-user-verification-template',
  templateUrl: './user-verification-template.component.html',
  styleUrls: ['./user-verification-template.component.sass']
})
export class UserVerificationTemplateComponent implements OnInit{
  
  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',
    defaultFontName: 'Arial',
  }

  settingsForm!: FormGroup;
  settings: Setting[] = [];


  constructor(
    private formBuilder: FormBuilder,
    private userService: UserDataService,
    private settingsService: SettingService,
    private notificationService: NotificationService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.settingsForm = this.formBuilder.group({
      CUSTOMER_VERIFY_SUBJECT: [''],
      CUSTOMER_VERIFY_CONTENT: [''],
    });


      this.route.queryParamMap.subscribe(params => {
        const token = params.get('token');
        if (token) {
          this.userService.verifyEmail(token).subscribe(
            response => {
              console.log(response);
              this.router.navigate(['/message'], { queryParams: { message: response } });
              
            },
            error => {
              console.error(error);
              let response = "Email verification failed";
              this.router.navigate(['/message'], { queryParams: { message: response} });

            }
          );
        } else {
          console.log('No token provided');
          // Handle case where no token is provided
        }
      });
   

    this.settingsService.getAllSettings().subscribe(settings => {
      this.settings = settings;

      this.settingsForm.patchValue({
        CUSTOMER_VERIFY_SUBJECT: this.getSettingValue('CUSTOMER_VERIFY_SUBJECT'),
        CUSTOMER_VERIFY_CONTENT: this.getSettingValue('CUSTOMER_VERIFY_CONTENT'),
      });
    });
  }

  get customerVerifyContent(): string | undefined {
    return this.settingsForm.get('CUSTOMER_VERIFY_CONTENT')?.value;
  }

  private getSettingValue(key: string): string | undefined {
    const setting = this.settings.find(s => s.key === key);
    return setting ? setting.value : undefined;
  }

  saveMailServerTemplate(): void {
    const formData = this.settingsForm.value;
    const updatedSettings: Setting[] = [
      { key: 'CUSTOMER_VERIFY_SUBJECT', value: formData.CUSTOMER_VERIFY_SUBJECT, category: 'MAIL_TEMPLATES' },
      { key: 'CUSTOMER_VERIFY_CONTENT', value: formData.CUSTOMER_VERIFY_CONTENT, category: 'MAIL_TEMPLATES'}
    ];

    this.settingsService.saveMailTemplateSettings(updatedSettings).subscribe(
      response => {
        console.log(response);
        this.notificationService.notify(
          NotificationType.SUCCESS,
          'Settings Updated Successfully'
        );
      },
      error => {
        console.error(error);
        this.notificationService.notify(
          NotificationType.ERROR,
          'Settings Update Failed'
        );
      }
    );
  }
  
}