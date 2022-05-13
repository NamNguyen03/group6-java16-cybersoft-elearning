export class CourseCreate{

    courseName: string;
    courseTime: number;
    description: string;

    constructor(coursename: string, coursetime: number, description: string){
        this.courseName = coursename;
        this.courseTime = coursetime;
        this.description = description;
    }
    // starAVG: number;
    // img : string;
    // level : string;
    // skills: string []=[];
    // category: string;


}
export class CourseRp{
    id!: string;
    courseName!: string;
    courseTime!: number;
    description!: string;
}

export class CourseUpdateInformation {
    courseName!: string;
    courseTime!: number;
    description!: string;

    constructor(coursename: string, coursetime: number, description: string){
        this.courseName = coursename;
        this.courseTime = coursetime;
        this.description = description;
}
}


