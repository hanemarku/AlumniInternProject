import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CountryComponent } from './country/country.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { EventDeleteComponent } from './event-features/event-delete/event-delete.component';
import { EventDetailComponent } from "./event-features/event-detail/event-detail.component";
import { EventEditComponent } from './event-features/event-edit/event-edit.component';
import { EventFormComponent } from './event-features/event-form/event-form.component';
import { EventSearchByComponent } from './event-features/event-search-by/event-search-by.component';
import { EventSpecificsComponent } from './event-features/event-specifics/event-specifics.component';
import { EventComponent } from "./event/event.component";
import { AuthenticationGuard } from './guard/authentication.guard';
import { NotLoggedInGuard } from './guard/not-logged-in-guard.guard';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { InterestListComponent } from './interest/interest.component';
import { SkillListComponent } from './skill/skill.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SigninComponent } from './user/signin/signin.component';
import { SingupComponent } from './user/singup/singup.component';

const routes: Routes = [
  {path: 'users', component: ListUsersComponent, canActivate: [AuthenticationGuard]},
  {path: 'skills', component: SkillListComponent, canActivate: [AuthenticationGuard] },
  {path: 'interests', component: InterestListComponent, canActivate: [AuthenticationGuard]},
  {path: 'signup', component: SingupComponent, canActivate: [NotLoggedInGuard]},
  {path: 'homepage', component: HomepageComponent, canActivate: [AuthenticationGuard]},
  {path: 'signin', component: SigninComponent, canActivate: [NotLoggedInGuard]},
  {path: 'profile', component: UserProfileComponent, canActivate: [AuthenticationGuard]},
  {path: 'error-page', component: ErrorPageComponent},
  {path: 'header', component: HeaderComponent},
  {path: 'countries', component: CountryComponent, canActivate: [AuthenticationGuard]},
  {path: 'event', component: EventComponent},
  {path:'event-detail', component: EventDetailComponent},
  {path:'event-form', component: EventFormComponent},
  {path:'event-edit', component:EventEditComponent},
  {path:'event-delete', component:EventDeleteComponent},
  {path:'event-search-by', component:EventSearchByComponent},
  {path: 'event-specifics', component: EventSpecificsComponent},
  {path: '', redirectTo: '/signin', pathMatch: 'full'},

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
