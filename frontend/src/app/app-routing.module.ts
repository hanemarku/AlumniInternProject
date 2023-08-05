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

const routes: Routes = [
  {path: 'users', component: ListUsersComponent, canActivate: [AuthenticationGuard]},
  {path: 'skills', component: SkillListComponent, canActivate: [AuthenticationGuard] },
  {path: 'interests', component: InterestListComponent, canActivate: [AuthenticationGuard]},
  {path: 'signup', component: SingupComponent, canActivate: [NotLoggedInGuard]},
  {path: 'signin', component: SigninComponent, canActivate: [NotLoggedInGuard]},
  {path: 'error-page', component: ErrorPageComponent},
  {path: '', redirectTo: '/signin', pathMatch: 'full'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
