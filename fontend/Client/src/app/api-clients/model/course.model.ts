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
}



