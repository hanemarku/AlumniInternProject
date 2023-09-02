import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NotificationType } from 'src/app/enum/header-type.enum';
import { NotificationService } from 'src/app/services/notification-service/notification.service';
import { Setting, SettingService } from 'src/app/services/setting-service/setting.service';

@Component({
  selector: 'app-mail-server',
  templateUrl: './mail-server.component.html',
  styleUrls: ['./mail-server.component.sass']
})
export class MailServerComponent implements OnInit{
  mailServerForm!: FormGroup;
  settings: Setting[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private settingsService: SettingService,
    private notificationService: NotificationService) {}

  ngOnInit(): void {

    this.mailServerForm = this.formBuilder.group({
      MAIL_HOST: [''],
      MAIL_PORT: [''],
      MAIL_USERNAME: [''],
      MAIL_PASSWORD: [''],
      SMTP_AUTH: ['true'],
      SMTP_SECURED: ['true'],
      MAIL_FROM: [''],
      MAIL_SENDER_NAME: ['']
    });


    this.settingsService.getAllSettings().subscribe(settings => {
      this.settings = settings;

      this.mailServerForm.patchValue({
        MAIL_HOST: this.getSettingValue('MAIL_HOST'),
        MAIL_PORT: this.getSettingValue('MAIL_PORT'),
        MAIL_USERNAME: this.getSettingValue('MAIL_USERNAME'),
        MAIL_PASSWORD: this.getSettingValue('MAIL_PASSWORD'),
        SMTP_AUTH: this.getSettingValue('SMTP_AUTH') || 'true',
        SMTP_SECURED: this.getSettingValue('SMTP_SECURED') || 'true',
        MAIL_FROM: this.getSettingValue('MAIL_FROM'),
        MAIL_SENDER_NAME: this.getSettingValue('MAIL_SENDER_NAME')
      });
    });
  }



  private getSettingValue(key: string): string | undefined {
    const setting = this.settings.find(s => s.key === key);
    return setting ? setting.value : undefined;
  }


  saveMailServerSettings(): void {
    const formData = this.mailServerForm.value;
  
    const updatedSettings: Setting[] = [
      { key: 'MAIL_HOST', value: formData.MAIL_HOST, category: 'MAIL_SERVER' },
      { key: 'MAIL_PORT', value: formData.MAIL_PORT, category: 'MAIL_SERVER' },
      { key: 'MAIL_USERNAME', value: formData.MAIL_USERNAME, category: 'MAIL_SERVER' },
      { key: 'MAIL_PASSWORD', value: formData.MAIL_PASSWORD, category: 'MAIL_SERVER' },
      { key: 'SMTP_AUTH', value: formData.SMTP_AUTH.toString(), category: 'MAIL_SERVER' },
      { key: 'SMTP_SECURED', value: formData.SMTP_SECURED.toString(), category: 'MAIL_SERVER' },
      { key: 'MAIL_FROM', value: formData.MAIL_FROM, category: 'MAIL_SERVER' },
      { key: 'MAIL_SENDER_NAME', value: formData.MAIL_SENDER_NAME, category: 'MAIL_SERVER' }
    ];
    
    this.settingsService.saveMailServerSettings(updatedSettings).subscribe(
      response => {
        console.log(response);
        this.notificationService.notify(
          NotificationType.SUCCESS,
          'Mail Server Settings Updated Successfully'
        );
      },
      error => {
        console.error(error);
        this.notificationService.notify(
          NotificationType.ERROR,
          'Mail Server Settings Update Failed'
        );
      }
    );
  }
  
  

}
