import { UserCommentResponse } from "./user.model";

export  class CommentCreate{
    content:string;
    lessonId:string;
    
    constructor(content:string,lessonId:string){
        this.lessonId=lessonId;
        this.content=content;

    }
}

export class CommentResponse{
    id:string;
    content:string;
    user:UserCommentResponse;
    createdAt:Date;
    constructor(id:string,content:string,user:UserCommentResponse,createdAt:Date){
        this.id=id;
        this.content=content;
        this.user=user;
        this.createdAt=createdAt;
    }
}
export  class RatingCreate{
    value:number;
    lessonId:string;
    
    constructor(value:number,lessonId:string){
        this.lessonId=lessonId;
        this.value=value;

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