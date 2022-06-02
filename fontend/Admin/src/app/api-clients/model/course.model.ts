import { LessonRp } from "./lesson.model";

export class CourseCreate{

    courseName: string;
    description: string;
    category: string;
    level: string;
    img: string;
    skill1: string;
    skill2: string;
    skill3: string;
    skill4: string;
    skill5: string;

    constructor(coursename: string, description: string,category: string,level: string,
        img: string,skill1: string,skill2: string,skill3: string,skill4: string,skill5: string){
        this.courseName = coursename;
        this.description = description;
        this.category= category;
        this.level = level;
        this.img= img;
        this.skill1= skill1;
        this.skill2= skill2;
        this.skill3= skill3;
        this.skill4= skill4;
        this.skill5= skill5;
    }
}
export class CourseRp{
    id!: string;
    courseName!: string;
    courseTime!: string;
    description!: string;
    img: string;
    category: string;
    level: string;
    starAvg: number;
    totalStar: number;
    totalRating: number;
    skill1: string;
    skill2: string;
    skill3: string;
    skill4: string;
    skill5: string;
    courseTime:number;
    lessons: LessonRp[];
}

export class CourseUpdateInformation {
    courseName: string;
    description: string;
    category: string;
    level: string;
    img: string;
    skill1: string;
    skill2: string;
    skill3: string;
    skill4: string;
    skill5: string;

    constructor(coursename: string, description: string,category: string,level: string,
        img: string,skill1: string,skill2: string,skill3: string,skill4: string,skill5: string){
        this.courseName = coursename;
        this.description = description;
        this.category= category;
        this.level= level;
        this.img= img;
        this.skill1= skill1;
        this.skill2= skill2;
        this.skill3= skill3;
        this.skill4= skill4;
        this.skill5= skill5;
    }
}


