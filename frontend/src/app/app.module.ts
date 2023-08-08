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
import { UpdateCountryDialogComponent } from './country/update-country-dialog/update-country-dialog.component';

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
<<<<<<< HEAD
    SigninComponent,
    ErrorPageComponent,
    HeaderComponent,
    HomepageComponent,
    UserProfileComponent,
    FooterComponent,
=======
    UpdateCountryDialogComponent,
>>>>>>> feature-countries-added
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
    NotificationModule
  ],
  providers: [AuthenticationGuard],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
