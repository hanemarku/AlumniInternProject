import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SingupComponent } from './user/singup/singup.component';
import { EducationComponent } from './education/education.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';
import { SkillListComponent } from './skill/skill.component';
import { InterestListComponent } from './interest/interest.component';

const routes: Routes = [
  {path: 'users', component: ListUsersComponent},
  {path: 'skills', component: SkillListComponent},
  {path: 'interests', component: InterestListComponent},
  {path: 'signup', component: SingupComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
