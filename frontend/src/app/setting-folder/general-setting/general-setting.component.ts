import { Component, OnInit } from '@angular/core';
import { SettingService } from 'src/app/services/setting-service/setting.service';


@Component({
  selector: 'app-general-setting',
  templateUrl: './general-setting.component.html',
  styleUrls: ['./general-setting.component.sass']
})
export class GeneralSettingComponent implements OnInit{
  settings: any = {}; 

  constructor(
    private settingsService: SettingService,
  ) {}

  ngOnInit(): void {

  }

  // saveSettings(): void {
  //   this.settingsService.saveGeneralSettings(this.settings).subscribe(
  //     response => {
  //       console.log('Settings saved:', response);
  //       console.log('Settings saved:', response);
  //     },
  //     error => {
  //       console.error('Error saving settings:', error);
  //     }
  //   );
  // }

}
