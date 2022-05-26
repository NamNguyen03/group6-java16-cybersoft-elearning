export class UserStatusComment{
    id?: string;
    displayName? : string;
}

export class CourseStatusComment{
    id? : string;
    courseName?: string;
}

export class StatusCommentRp{
    id?: string;
    status?: string;
    user?: UserStatusComment;
    course?: CourseStatusComment;
}