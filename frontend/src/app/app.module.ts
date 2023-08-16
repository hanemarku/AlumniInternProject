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
    CommonModule
  ],
  providers: [AuthenticationGuard],

  bootstrap: [AppComponent]
})
export class AppModule { }
