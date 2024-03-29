export class UserStatusComment{
    id?: string;
    displayName? : string;
}

export class CourseStatusComment{
    id? : string;
    courseName?: string;
    lessons: LessonCommentRp[];
}

export class StatusCommentRp{
    id?: string;
    status?: string;
    user?: UserStatusComment;
    course?: CourseStatusComment;
}

export class LessonCommentRp{
    id?: string;
    name?: string;
    comments: CommentRp[];
}

export class CommentRp{
    id?: string;
    content?: string;    
}

export class CommentLesson{
    id: string;
    content: string;  
    lessonName: string;
    
    constructor(id: string, content: string, lessonName: string){
        this.id = id;
        this.content = content;
        this.lessonName = lessonName;
    }
}