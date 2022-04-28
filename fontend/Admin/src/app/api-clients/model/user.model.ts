export class UserCreate {

    username: string;
    password: string;
    displayName: string;
    email: string;
    status: string;
    firstName: string;
    lastName: string;
    department: string;
    major: string;

    constructor(username: string, password: string, displayName: string, email: string, status: string, firstName: string, lastName: string, department: string, major: string){
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.major = major;
    }
}

export class UserRp{
    id: string;
    username: string;
    displayName: string;
    email: string;
    firstName: string;
    lastName: string;
    avatar: string;
    department: string;
    major: string;
    hobbies: string;
    facebook: string;
    status: string;
    gender: string;
    phone: string;

    constructor(){
       this.id = "";
       this.username = "";
       this.displayName = "";
       this.firstName = "";
       this.lastName = "";
       this.avatar = "";
       this.department = "";
       this.major = "";
       this.hobbies = "";
       this.facebook = "";
       this.status = "";
       this.gender = "";
       this.phone = "";
       this.email = "";

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

export class LoginResponse{
    jwt: string;
    displayName: string;
}

export class UpdateMyProfileRq{
    
    displayName: string;
    email: string;
    firstName: string;
    lastName: string;
    hobbies: string;
    facebook: string;
    gender: string;
    phone: string;

    constructor(displayName: string, email: string, firstName: string, lastName: string, hobbies: string, facebook: string, gender: string, phone: string){
        this.displayName = displayName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobbies = hobbies;
        this.facebook = facebook;
        this.gender = gender;
        this.phone = phone;
    }
}