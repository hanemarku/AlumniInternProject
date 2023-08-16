import { UserProfileComponent } from "../user-profile/user-profile.component";

export interface UserEvents{
 //miguht also be needed as number and by number show the role, thats how it is saved in the db
    membershipRole: string;
    user: UserProfileComponent;
}