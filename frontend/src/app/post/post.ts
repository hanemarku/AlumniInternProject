import { User } from "../services/user-service/user-data.service";

export class Post {
    author: User = {
        firstname: "",
        lastname: "",
        email: "",
        password: "",
        birthday: new Date,
        city: "",
        country: "",
        skills: [],
        interests: [],
        educationHistories: [],
        employmentHistories: [],
        profilePicUrl: "",
        phoneNumber: "",
        bio: ""
    };
    content: string = '';
    postCreation = new Date;
    porfilPicUrl: string = '';
  value: any;

}
