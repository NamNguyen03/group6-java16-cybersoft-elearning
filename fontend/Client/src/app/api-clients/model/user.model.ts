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