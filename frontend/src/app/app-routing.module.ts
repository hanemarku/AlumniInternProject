import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CityComponent } from './city/city.component';
import { CountryComponent } from './country/country.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { EventDeleteComponent } from './event-features/event-delete/event-delete.component';
import { EventDetailComponent } from "./event-features/event-detail/event-detail.component";
import { EventEditComponent } from './event-features/event-edit/event-edit.component';
import { EventFormComponent } from './event-features/event-form/event-form.component';
import { EventRegisterComponent } from './event-features/event-register/event-register.component';
import { EventSearchByComponent } from './event-features/event-search-by/event-search-by.component';
import { EventSpecificsComponent } from './event-features/event-specifics/event-specifics.component';
import { EventComponent } from "./event/event.component";
import { AuthenticationGuard } from './guard/authentication.guard';
import { NotLoggedInGuard } from './guard/not-logged-in-guard.guard';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { InterestListComponent } from './interest/interest.component';
import { ProfileSliderComponent } from './profile-slider/profile-slider.component';
import { ProfilesSwiperComponent } from './profiles-swiper/profiles-swiper.component';
import { SkillListComponent } from './skill/skill.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ForgotPasswordComponent } from './user/forgot-password/forgot-password.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { NotificationsComponent } from './user/notifications/notifications.component';
import { ResetPasswordComponent } from './user/reset-password/reset-password.component';
import { SigninComponent } from './user/signin/signin.component';
import { SingupComponent } from './user/singup/singup.component';
import { SuggestionsComponent } from './user/suggestions/suggestions.component';


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
  {path: 'event', component: EventComponent, canActivate: [AuthenticationGuard]},
  {path:'event-detail', component: EventDetailComponent, canActivate: [AuthenticationGuard]},
  {path:'event-form', component: EventFormComponent, canActivate: [AuthenticationGuard]},
  {path:'event-edit', component:EventEditComponent, canActivate: [AuthenticationGuard]},
  {path:'event-delete', component:EventDeleteComponent, canActivate: [AuthenticationGuard]},
  {path:'event-search-by', component:EventSearchByComponent, canActivate: [AuthenticationGuard]},
  {path: 'event-specifics', component: EventSpecificsComponent, canActivate: [AuthenticationGuard]},
  {path: 'event-register', component: EventRegisterComponent},
  {path: 'cities', component: CityComponent, canActivate: [AuthenticationGuard]},
  {path: 'notifications', component: NotificationsComponent, canActivate: [AuthenticationGuard]},
  {path: 'suggestions', component:SuggestionsComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'forgot-password', component: ForgotPasswordComponent},
  {path: 'profileSlider', component: ProfileSliderComponent},
  {path: 'profilesSwiper', component: ProfilesSwiperComponent},
  {path: '', redirectTo: '/signin', pathMatch: 'full'},

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
