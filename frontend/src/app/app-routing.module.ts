import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { SkillListComponent } from './skill/skill.component';
import { InterestListComponent } from './interest/interest.component';

const routes: Routes = [
  {path: 'users', component: ListUsersComponent},
  {path: 'skills', component: SkillListComponent},
  {path: 'interests', component: InterestListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
