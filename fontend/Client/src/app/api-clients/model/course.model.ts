import { CardLessonReponseClientDTO } from "./lesson.model";

export class CourseCreate {

    courseName: string;
    courseTime: number;
    description: string;

    constructor(courseName: string, courseTime: number, description: string) {
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
export class CourseRp {
    id?: string;
    courseName?: string;
    category?: string;
    courseTime?: number;
    img?: string;
    starAvg?: number;
    description?: string;
    createdBy?: string;
    totalRating?: number;
    level?: string;
    skill1?: string;
    skill2?: string;
    skill3?: string;
    skill4?: string;
    skill5?: string;
    idFirstLesson?: string;
    constructor() {
        this.id = "";
        this.courseName = "";
        this.category = "";
        this.courseTime = 0;
        this.img = "";
        this.starAvg = 0;
        this.description = "";
        this.createdBy = "";
        this.totalRating = 0;
        this.level = "";
        this.skill1 = "";
        this.skill2 = "";
        this.skill3 = "";
        this.skill4 = "";
        this.skill5 = "";
        this.idFirstLesson = "";

    }
}
export class CourseDetailsReponseClientDTO {
    id?: string;
    courseName?: string;
    category?: string;
    courseTime?: number;
    img?: string;
    starAvg?: number;
    description?: string;
    createdBy?: string;
    totalRating?: number;
    level?: string;
    skill1?: string;
    skill2?: string;
    skill3?: string;
    skill4?: string;
    skill5?: string;
    lessons?: CardLessonReponseClientDTO[];
    constructor() {
        this.id = "";
        this.courseName ="" ;
        this.category = "";
        this.courseTime = 0;
        this.img = "";
        this.starAvg = 0;
        this.description = "";
        this.createdBy = "";
        this.totalRating = 0;
        this.level = "";
        this.skill1 = "";
        this.skill2 = "";
        this.skill3 = "";
        this.skill4 = "";
        this.skill5 = "";
        this.lessons = [];

    }


}
