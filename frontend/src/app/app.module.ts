import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { SingupComponent } from './user/singup/singup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CountryComponent } from './country/country.component';
import { CityComponent } from './city/city.component';
import { EducationComponent } from './education/education.component';
import { DpDatePickerModule } from 'ng2-date-picker'; 
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CountryCitySelectorComponent } from './country-city-selector/country-city-selector.component';
import { SkillInterestComponentComponent } from './skill-interest-component/skill-interest-component.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';
import { FilterPipe } from './filter.pipe';
import { InterestSearchComponent } from './interest-search/interest-search.component'; 

@NgModule({
  declarations: [
    AppComponent,
    ListUsersComponent,
    SingupComponent,
    CountryComponent,
    CityComponent,
    EducationComponent,
    CountryCitySelectorComponent,
    SkillInterestComponentComponent,
    SkillSearchComponent,
    FilterPipe,
    InterestSearchComponent
  ],
  imports: [
    BrowserModule,
    DataTablesModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
