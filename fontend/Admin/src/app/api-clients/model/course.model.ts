import { LessonRp } from "./lesson.model";

export class CourseCreate{

    courseName: string;
    courseTime: number;
    description: string;

    constructor(coursename: string, coursetime: number, description: string){
        this.courseName = coursename;
        this.courseTime = coursetime;
        this.description = description;
    }
}
export class CourseRp{
    id!: string;
    courseName!: string;
    courseTime!: number;
    description!: string;
    lessons: LessonRp[];
}

export class CourseUpdateInformation {
    courseName: string;
    courseTime: number;
    description: string;

    constructor(courseName: string, courseTime: number, description: string){
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.description = description;
    }
}


