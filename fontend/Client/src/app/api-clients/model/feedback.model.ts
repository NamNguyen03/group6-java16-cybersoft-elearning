import { UserCommentResponse } from "./user.model";

export  class CommentCreate{
    content:string;
    userId:string;
    lessonId:string;
    
    constructor(content:string,userId:string,lessonId:string){
        this.userId=userId;
        this.lessonId=lessonId;
        this.content=content;

    }
}

export class CommentResponse{
    id:string;
    content:string;
    user:UserCommentResponse;
    timeComment:Date;
    constructor(id:string,content:string,user:UserCommentResponse,timeComment:Date){
        this.id=id;
        this.content=content;
        this.user=user;
        this.timeComment=timeComment;
    }
}
export  class RatingCreate{
    value:number;
    userId:string;
    lessonId:string;
    
    constructor(){
        this.userId="";
        this.lessonId="";
        this.value=5;

    }
}
export class RatingResponse{
    id:string;
    value:number;
    user:UserCommentResponse;
    constructor(id:string,value:number,user:UserCommentResponse){
        this.id=id;
        this.value=value;
        this.user=user;
        }
}