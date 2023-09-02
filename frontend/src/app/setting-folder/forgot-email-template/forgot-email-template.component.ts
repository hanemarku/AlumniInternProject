import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { Setting, SettingService } from 'src/app/services/setting-service/setting.service';
import { UserDataService } from 'src/app/services/user-service/user-data.service';

@Component({
  selector: 'app-forgot-email-template',
  templateUrl: './forgot-email-template.component.html'
})

export class ForgotEmailTemplateComponent implements OnInit {

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
      FORGOT_PASSWORD_SUBJECT: [''],
      FORGOT_PASSWORD_CONTENT: [''],
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
        }
      });
   

    this.settingsService.getAllSettings().subscribe(settings => {
      this.settings = settings;

      this.settingsForm.patchValue({
        FORGOT_PASSWORD_SUBJECT: this.getSettingValue('FORGOT_PASSWORD_SUBJECT'),
        FORGOT_PASSWORD_CONTENT: this.getSettingValue('FORGOT_PASSWORD_CONTENT'),
      });
    });
  }

  get customerVerifyContent(): string | undefined {
    return this.settingsForm.get('FORGOT_PASSWORD_CONTENT')?.value;
  }

  private getSettingValue(key: string): string | undefined {
    const setting = this.settings.find(s => s.key === key);
    console.log(setting);
    return setting ? setting.value : undefined;
  }

  saveMailServerTemplate(): void {
    const formData = this.settingsForm.value;
    const updatedSettings: Setting[] = [
      { key: 'FORGOT_PASSWORD_SUBJECT', value: formData.FORGOT_PASSWORD_SUBJECT, category: 'MAIL_TEMPLATES' },
      { key: 'FORGOT_PASSWORD_CONTENT', value: formData.FORGOT_PASSWORD_CONTENT, category: 'MAIL_TEMPLATES'}
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
