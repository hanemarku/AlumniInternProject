import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DataTablesModule } from 'angular-datatables';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CityComponent } from './city/city.component';
import { CountryCitySelectorComponent } from './country-city-selector/country-city-selector.component';
import { CountryComponent } from './country/country.component';
import { UpdateCountryDialogComponent } from './country/update-country-dialog/update-country-dialog.component';
import { EducationComponent } from './education/education.component';
import { EmploymentComponent } from './employment/employment.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { EventDeleteComponent } from './event-features/event-delete/event-delete.component';
import { EventDetailComponent } from "./event-features/event-detail/event-detail.component";
import { EventEditComponent } from './event-features/event-edit/event-edit.component';
import { EventFormComponent } from './event-features/event-form/event-form.component';
import { EventSearchByComponent } from './event-features/event-search-by/event-search-by.component';
import { EventSpecificsComponent } from './event-features/event-specifics/event-specifics.component';
import { EventComponent } from "./event/event.component";
import { FilterPipe } from './filter.pipe';
import { FooterComponent } from './footer/footer.component';
import { AuthenticationGuard } from './guard/authentication.guard';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { InterestSearchComponent } from './interest-search/interest-search.component';
import { EditInterestDialogComponent } from './interest/edit-interest-dialog/edit-interest-dialog.component';
import { InterestListComponent } from './interest/interest.component';
import { NotificationModule } from './notification.module';
import { SkillInterestComponentComponent } from './skill-interest-component/skill-interest-component.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';
import { EditSkillDialogComponent } from './skill/edit-skill-dialog/edit-skill-dialog.component';
import { SkillListComponent } from './skill/skill.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SigninComponent } from './user/signin/signin.component';
import { SingupComponent } from './user/singup/singup.component';
import { SuccessPageComponent } from './user/success-page/success-page.component';
import { UpdateCityDialogComponent } from './city/update-city-dialog/update-city-dialog.component';
import { MessageComponent } from './message/message.component';
import { SettingsComponent } from './setting-folder/settings/settings.component';
import { GeneralSettingComponent } from './setting-folder/general-setting/general-setting.component';
import { MailServerComponent } from './setting-folder/mail-server/mail-server.component';
import { UserVerificationTemplateComponent} from './setting-folder/email-template/user-verification-template.component';
import { AccountVerificationComponent } from './account-verification/account-verification.component';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ResetPasswordComponent } from './user/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './user/forgot-password/forgot-password.component';
import { ForgotEmailTemplateComponent } from './setting-folder/forgot-email-template/forgot-email-template.component';
import { SuggestionsComponent } from './user/suggestions/suggestions.component';
import { NotificationsComponent } from './user/notifications/notifications.component';
import { ProfileSliderComponent } from './profile-slider/profile-slider.component';
import { NotificationService } from './services/notification-service/notification.service';
import { AuthenticationService } from './services/authenication-service/authentication.service';
import { UserDataService } from './services/user-service/user-data.service';
import { ChatComponent } from './chat/chat.component';
import { UserListComponent } from './user-list/user-list.component';
import { PrivateChatWindowComponent } from './private-chat-window/private-chat-window.component';
import { RoomComponent } from './room/room.component';
import { ReversePipe } from './services/ReversePipe';
import { SidenavComponent } from './sidenav/sidenav.component';
import { AdminSettingComponent } from './admin-setting/admin-setting.component';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { EditRecommendationDialogComponent } from './recommendation/edit-recommendation-dialog/edit-recommendation-dialog.component';

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
    UpdateCountryDialogComponent,
    EventComponent,
    EventDetailComponent,
    EventFormComponent,
    EventEditComponent,
    EventDeleteComponent,
    EventSearchByComponent,
    EventSpecificsComponent,
    UpdateCityDialogComponent,
    ResetPasswordComponent,
    ForgotPasswordComponent,
    UserVerificationTemplateComponent,
    ForgotEmailTemplateComponent,
    SuggestionsComponent,
    NotificationsComponent,
    ProfileSliderComponent,
    MessageComponent,
    SettingsComponent,
    GeneralSettingComponent,
    MailServerComponent,
    AccountVerificationComponent,
    ChatComponent,
    UserListComponent,
    PrivateChatWindowComponent,
    RoomComponent,
    ReversePipe,
    SidenavComponent,
    AdminSettingComponent,
    RecommendationComponent,
    EditRecommendationDialogComponent,
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
    CommonModule,
    AngularEditorModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
  ],
  providers: [NotificationService ,AuthenticationGuard,
    AuthenticationService, UserDataService
  ],

  // providers: [NotificationService ,AuthenticationGuard,
  //   AuthenticationService, UserDataService,
  //   {
  //     provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true
  //   }
  // ],

  bootstrap: [AppComponent]
})
export class AppModule { }
