import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SkillListComponent } from './skill/skill.component';
import { EditSkillDialogComponent } from './skill/edit-skill-dialog/edit-skill-dialog.component';
import { EditInterestDialogComponent } from './interest/edit-interest-dialog/edit-interest-dialog.component';
import { InterestListComponent } from './interest/interest.component';
import { SingupComponent } from './user/singup/singup.component';
import { CountryComponent } from './country/country.component';
import { CityComponent } from './city/city.component';
import { EducationComponent } from './education/education.component';
import { CountryCitySelectorComponent } from './country-city-selector/country-city-selector.component';
import { SkillInterestComponentComponent } from './skill-interest-component/skill-interest-component.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';
import { FilterPipe } from './filter.pipe';
import { InterestSearchComponent } from './interest-search/interest-search.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { EmploymentComponent } from './employment/employment.component';
import { SuccessPageComponent } from './user/success-page/success-page.component';
import { SigninComponent } from './user/signin/signin.component';
import { NotificationModule } from './notification.module';
import { NotificationService } from './services/notification-service/notification.service';
import { UserDataService } from './services/user-service/user-data.service';
import { AuthenticationGuard } from './guard/authentication.guard';
import { ErrorPageComponent } from './error-page/error-page.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { FooterComponent } from './footer/footer.component';
import { MessageComponent } from './message/message.component';
import { SettingsComponent } from './setting-folder/settings/settings.component';
import { GeneralSettingComponent } from './setting-folder/general-setting/general-setting.component';
import { MailServerComponent } from './setting-folder/mail-server/mail-server.component';
import { UserVerificationTemplateComponent} from './setting-folder/email-template/user-verification-template.component';
import { AccountVerificationComponent } from './account-verification/account-verification.component';
import { UpdateCountryDialogComponent } from './country/update-country-dialog/update-country-dialog.component';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ResetPasswordComponent } from './user/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './user/forgot-password/forgot-password.component';
import { ForgotEmailTemplateComponent } from './setting-folder/forgot-email-template/forgot-email-template.component';
import { SuggestionsComponent } from './user/suggestions/suggestions.component';
import { NotificationsComponent } from './user/notifications/notifications.component';
import { ProfileSliderComponent } from './profile-slider/profile-slider.component';
import { ProfilesSwiperComponent } from './profiles-swiper/profiles-swiper.component';



@NgModule({
  declarations: [
    AppComponent,
    ListUsersComponent,
    SkillListComponent,
    EditSkillDialogComponent,
    EditInterestDialogComponent,
    InterestListComponent,
    SingupComponent,
    CountryComponent,
    CityComponent,
    EducationComponent,
    CountryCitySelectorComponent,
    SkillInterestComponentComponent,
    SkillSearchComponent,
    FilterPipe,
    InterestSearchComponent,
    EmploymentComponent,
    SuccessPageComponent,
    SigninComponent,
    ErrorPageComponent,
    HeaderComponent,
    HomepageComponent,
    UserProfileComponent,
    FooterComponent,
    SettingsComponent,
    GeneralSettingComponent,
    MailServerComponent,
    AccountVerificationComponent,
    MessageComponent,
    UpdateCountryDialogComponent,
    ResetPasswordComponent,
    ForgotPasswordComponent,
    UserVerificationTemplateComponent,
    ForgotEmailTemplateComponent,
    SuggestionsComponent,
    NotificationsComponent,
    ProfileSliderComponent,
    ProfilesSwiperComponent

  ],
  imports: [
    BrowserModule,
    DataTablesModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgbModule,
    MatDialogModule,
    NotificationModule,
    AngularEditorModule
  ],
  providers: [AuthenticationGuard],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
