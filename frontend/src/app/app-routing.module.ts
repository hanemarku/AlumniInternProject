import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateEventsComponent } from './create-events/create-events.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { EventDetailComponent } from './event-detail/event-detail.component';
import { EventsComponent } from './events/events.component';
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
  {path: 'events', component: EventsComponent},
  {path: 'event-details', component: EventDetailComponent},
  {path: 'create-events', component: CreateEventsComponent},
  {path: '', redirectTo: '/signin', pathMatch: 'full'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
