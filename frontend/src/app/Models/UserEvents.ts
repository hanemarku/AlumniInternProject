//import { User } from "../services/user-service/user-data.service";
import { MembershipRole } from "../enum/membership-role.enum";
import { Status } from "../enum/status.enum";

export interface UserEvents{
    //id:string;
    userId: string;
    eventSpecificsId: string;
    membershipRole: MembershipRole;
    status: Status;
}
