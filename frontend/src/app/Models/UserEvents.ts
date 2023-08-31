//import { User } from "../services/user-service/user-data.service";
import { MembershipRole } from "../enum/membership-role.enum";
import { Status } from "../enum/status.enum";
import { UserList } from "../user/list-users/list-users.component";
import { EventSpecifics } from "./EventSpecifics";

export interface UserEvents{
    id:string;
    user: UserList;
    eventSpecifics: EventSpecifics;
    membershipRole: MembershipRole;
    status: Status;
}