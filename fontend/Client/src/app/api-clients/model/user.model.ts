import { CourseRp } from "./course.model";

export class UserRp{
 
    id: string;
    username: string;
    displayName: string;
    email: string;
    firstName: string;
    lastName: string;
    avatar: string;
    constructor(){
       this.id = "";
       this.username = "";
       this.displayName = "";
       this.firstName = "";
       this.lastName = "";
       this.avatar = "";
       this.email = "";

    }
}

export class InstructorCourseClientDTO{
    username: string;
    displayName: string;
    createBy: string;
    lastModifiedBy: string;
    lastModifiedAt: string;
    email: string;
    hobbies: string;
    facebook: string;
    gender: string;
    phone: string;
    department: string;
    firstName: string;
    lastName: string;
    avatar: string;
    courses?: CourseRp[];
    constructor(){
       this.username = "";
       this.displayName = "";
       this.firstName = "";
       this.lastName = "";
       this.avatar = "";
       this.email = "";
       this.createBy="";
       this.lastModifiedBy="";
       this.lastModifiedAt="";
       this.facebook="";
       this.hobbies = "";
       this.gender ="";
       this.phone = "";
       this.department="";
       this.courses=[];

    }
}


export class LoginRequest{
    username: string;
    password: string;

    constructor(username: string, password: string){
        this.username = username;
        this.password = password;
    }
}

export class RegisterRequest{
    displayName: string;
    email: string;
    firstName: string;
    lastName: string;
    username: string;
    password: string;

    constructor(displayName: string, email: string, firstName: string, lastName: string, username: string, password: string){
        this.displayName = displayName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}

export class UserCommentResponse{
    id:string;
    avatar: string;
    displayName: string;
    
    constructor(id:string,avatar:string,displayName:string){
        this.id=id;
        this.avatar=avatar;
        this.displayName=displayName;
    }
}