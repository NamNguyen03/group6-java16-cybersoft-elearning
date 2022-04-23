export class CourseCreate {

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
    coursename!: string;
    coursetime!: number;
    description!: string;
}

