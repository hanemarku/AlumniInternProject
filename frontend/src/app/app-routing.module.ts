import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SingupComponent } from './user/singup/singup.component';
import { EducationComponent } from './education/education.component';
import { SkillSearchComponent } from './skill-search/skill-search.component';

const routes: Routes = [
  {path: 'users', component: ListUsersComponent},
  {path: 'signup', component: SingupComponent},
  {path: 'skillSearch', component: SkillSearchComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
