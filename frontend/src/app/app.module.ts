import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { SkillListComponent } from './skill/skill.component';
import { EditSkillDialogComponent } from './skill/edit-skill-dialog/edit-skill-dialog.component';
import { EditInterestDialogComponent } from './interest/edit-interest-dialog/edit-interest-dialog.component';
import { InterestListComponent } from './interest/interest.component';


@NgModule({
  declarations: [
    AppComponent,
    ListUsersComponent,
    SkillListComponent,
    EditSkillDialogComponent,
    EditInterestDialogComponent,
    InterestListComponent
  ],
  imports: [
    BrowserModule,
    DataTablesModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
