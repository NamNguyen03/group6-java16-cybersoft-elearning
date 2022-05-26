import { LessonRp } from "./lesson.model";

export class CourseCreate{

    courseName: string;
    courseTime: number;
    description: string;

    constructor(courseName: string, courseTime: number, description: string){
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.description = description;
    }
    // starAVG: number;
    // img : string;
    // level : string;
    // skills: string []=[];
    // category: string;


}
export class CourseRp{
    id?: string;
    courseName?: string;
    category?: string;
    courseTime?: number;
    img?: string;
    starAvg?: number ;
    description?: string;
    createdBy?: string;
    totalRating?: number;
    level?: string;
    skill1?: string;
    skill2?: string;
    skill3?: string;
    skill4?: string;
    skill5?: string;
    lessons:LessonRp [] =[];
    constructor(id: string,courseName: string,
        category: string,
        courseTime: number,
        img: string,
        starAvg: number,
        description: string,
        createdBy: string,
        totalRating: number,
        level: string,
        skill1: string,
        skill2: string,
        skill3: string
        ){
        this.id=id;
        this.courseName=courseName;
        this.category=category;
        this.courseTime = courseTime;
        this.createdBy=createdBy;
        this.img=img;
        this.starAvg=starAvg;
        this.description=description;
        this.totalRating=totalRating;
        this.level=level;
        this.skill1=skill1;
        this.skill2=skill2;
        this.skill3=skill3;
    }
}



