import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SingupComponent } from './user/singup/singup.component';
import { EducationComponent } from './education/education.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';
import { SkillListComponent } from './skill/skill.component';
import { InterestListComponent } from './interest/interest.component';
import { SigninComponent } from './user/signin/signin.component';
import { AuthenticationGuard } from './guard/authentication.guard';
import { NotLoggedInGuard } from './guard/not-logged-in-guard.guard';
import { ErrorPageComponent } from './error-page/error-page.component';
import { HomepageComponent } from './homepage/homepage.component';
import { HeaderComponent } from './header/header.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { CountryComponent } from './country/country.component';
import { PostComponent } from './post/post.component';

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
  {path: '', redirectTo: '/signin', pathMatch: 'full'},
  {path: 'post', component: PostComponent}

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
